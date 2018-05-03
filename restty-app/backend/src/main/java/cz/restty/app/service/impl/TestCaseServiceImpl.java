package cz.restty.app.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Log;
import cz.restty.app.entities.Parameter;
import cz.restty.app.entities.Project;
import cz.restty.app.entities.TestCase;
import cz.restty.app.enums.ParamType;
import cz.restty.app.repositories.LogRepository;
import cz.restty.app.repositories.TestCaseRepository;
import cz.restty.app.rest.dto.TestCaseDto;
import cz.restty.app.service.TestCaseService;

/**
 * Default implementation of {@link TestCaseService}.
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private LogRepository logRepository;

    @Override
    public boolean run(TestCase testCase) {
        RestTemplate restTemplate = new RestTemplate();
        
        Log log = new Log();
        log.setTestCase(testCase);
        log.setRun(LocalDateTime.now());
        log.setResponseStatus(HttpStatus.OK.toString());
        log.setSuccess(true);
        
        // TODO
//        testCase.get
        return false;
    }

//    Override
//    public boolean run(Endpoint endpoint) {
//
//        HttpHeaders headers = new HttpHeaders();
//        endpoint.getHeaders().stream().filter(header -> header.getEnabled()).forEach(endpointHeader -> {
//            headers.add(endpointHeader.getHeader().getHeader(), endpointHeader.getHeader().getValue());
//        });
//
//        try {
//            List<Parameter> pathVariables = endpoint.getParameters().stream()
//                    .filter(pathVariable -> ParamType.PATH.equals(pathVariable.getType())).collect(Collectors.toList());
//            String path = endpoint.getProject().getPath() + endpoint.getPath();
//            String resolvedPath = resolvePathVariables(path, pathVariables);
//
//            Optional<Parameter> body = endpoint.getParameters().stream().filter(parameter -> ParamType.BODY.equals(parameter.getType()))
//                    .findFirst();
//            JSONObject jsonBody = new JSONObject();
//            if (body.isPresent()) {
//                body.get().getModel().getAttributes().forEach(attr -> {
//                    try {
//                        jsonBody.put(attr.getName(), attr.getValue());
//                    } catch (JSONException e) {}
//                });
//            }
//
//            HttpEntity<String> entity = new HttpEntity<>(jsonBody.toString(), headers);
//
//            restTemplate.exchange(resolvedPath, endpoint.getMethod(), entity, String.class);
//        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
//            log.setSuccess(false);
//            log.setResponseStatus((httpClientOrServerExc).getStatusCode().toString());
//            log.setResponseMessage(httpClientOrServerExc.getMessage());
//        } catch (Exception ex) {
//            log.setSuccess(false);
//            log.setResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
//            log.setResponseMessage(ex.getMessage());
//        } finally {
//            logRepository.save(log);
//        }
//
//        return log.getSuccess();
//    }

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
            testCaseRepository.delete(testCase);
        });
    }

}
