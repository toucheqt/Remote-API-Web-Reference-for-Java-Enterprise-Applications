package cz.restty.app.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import cz.restty.app.entities.Log;
import cz.restty.app.entities.Parameter;
import cz.restty.app.entities.Project;
import cz.restty.app.entities.TestCase;
import cz.restty.app.entities.TestCaseSettings;
import cz.restty.app.enums.ParamType;
import cz.restty.app.repositories.LogRepository;
import cz.restty.app.repositories.TestCaseRepository;
import cz.restty.app.repositories.TestCaseSettingsRepository;
import cz.restty.app.rest.dto.TestCaseDto;
import cz.restty.app.service.TestCaseService;
import cz.restty.app.service.TestCaseSettingsService;

/**
 * Default implementation of {@link TestCaseService}.
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class TestCaseServiceImpl implements TestCaseService {

    private static final Logger logger = Logger.getLogger(TestCaseServiceImpl.class);

    @Autowired
    private TestCaseSettingsService testCaseSettingsService;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private TestCaseSettingsRepository testCaseSettingsRepository;

    @Override
    public boolean run(TestCase testCase) {
        RestTemplate restTemplate = new RestTemplate();
        
        Log log = new Log();
        log.setTestCase(testCase);
        log.setRun(LocalDateTime.now());
        log.setResponseStatus(HttpStatus.OK.toString());
        log.setSuccess(true);

        ResponseEntity<String> previousStep = null;

        // call all endpoints in the test case
        for (TestCaseSettings settings : testCase.getSettings().stream()
                .sorted((s1, s2) -> s1.getEndpointOrder().compareTo(s2.getEndpointOrder())).collect(Collectors.toList())) {
            HttpHeaders headers = new HttpHeaders();
            settings.getEndpoint().getHeaders().stream().filter(header -> header.getEnabled()).forEach(endpointHeader -> {
                headers.add(endpointHeader.getHeader().getHeader(), endpointHeader.getHeader().getValue());
            });

            try {
                List<Parameter> pathVariables = settings.getParameters()
                        .stream()
                        .filter(pathVariable -> ParamType.PATH.equals(pathVariable.getType()))
                        .collect(Collectors.toList());
                
                String path = settings.getEndpoint().getProject().getPath() + settings.getEndpoint().getPath();
                String resolvedPath = resolvePathVariables(path, pathVariables, settings.getUsePrevious(), previousStep);
                
                Optional<Parameter> body = settings.getParameters()
                        .stream()
                        .filter(parameter -> ParamType.BODY.equals(parameter.getType()))
                        .findFirst();
                
                JSONObject jsonBody = new JSONObject();
                if (body.isPresent()) {
                    body.get().getModel().getAttributes().forEach(attr -> {
                        try {
                            jsonBody.put(attr.getName(), attr.getValue());
                        } catch (JSONException e) {}
                    });
                }

                HttpEntity<String> entity = new HttpEntity<>(jsonBody.toString(), headers);
                previousStep = restTemplate.exchange(resolvedPath, settings.getEndpoint().getMethod(), entity, String.class);
            } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
                logger.warn(httpClientOrServerExc.getMessage());
                log.setSuccess(false);
                log.setResponseStatus((httpClientOrServerExc).getStatusCode().toString());
                log.setResponseMessage(httpClientOrServerExc.getMessage());
                settings.setFailed(true);
                testCaseSettingsRepository.save(settings);
                break;
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                log.setSuccess(false);
                log.setResponseStatus(HttpStatus.BAD_REQUEST.toString());
                log.setResponseMessage(ex.getMessage());
                settings.setFailed(true);
                testCaseSettingsRepository.save(settings);
                break;
            }

            settings.setFailed(false);
            testCaseSettingsRepository.save(settings);
        }
        
        return logRepository.save(log).getSuccess();
    }

    @Override
    public TestCaseDto createTestCase(Project project, TestCaseDto testCaseDto) {
        TestCase testCase = new TestCase();
        testCase.setName(testCaseDto.getName());
        testCase.setDescription(testCaseDto.getDescription());
        testCase.setProject(project);
        return new TestCaseDto(testCaseRepository.save(testCase));
    }

    @Override
    public void deleteAllByProject(Project project) {
        testCaseRepository.findAllByProject(project).forEach(testCase -> {
            logRepository.deleteAllByTestCase(testCase);
            testCaseSettingsService.removeAllByTestCase(testCase);
            testCaseRepository.delete(testCase);
        });
    }
    
    @Override
    public void deleteTestCases(Project project, List<Long> testCasesIds) {
        testCaseRepository.findAllById(testCasesIds).forEach(testCase -> {
            logRepository.deleteAllByTestCase(testCase);
            testCaseSettingsService.removeAllByTestCase(testCase);
            testCaseRepository.delete(testCase);
        });
    }

    private String resolvePathVariables(String path, List<Parameter> pathVariables, Boolean usePrevious,
            ResponseEntity<String> previousStep) {
        for (Parameter pathVariable : pathVariables) {
            if (usePrevious && previousStep != null && previousStep.getBody() != null) {
                try {
                    JSONObject body = new JSONObject(previousStep.getBody());
                    Object variable = body.get(pathVariable.getName());
                    if (variable != null) {
                        path = path.replaceAll("\\{" + pathVariable.getName() + "\\}", variable.toString());
                    }
                } catch (JSONException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            if (pathVariable.getParameterValue() != null) {
                path = path.replaceAll("\\{" + pathVariable.getName() + "\\}", pathVariable.getParameterValue());
            }
        }

        return path;
    }

}
