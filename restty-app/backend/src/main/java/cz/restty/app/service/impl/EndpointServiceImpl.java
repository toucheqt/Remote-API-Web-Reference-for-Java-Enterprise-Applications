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
import cz.restty.app.enums.ParamType;
import cz.restty.app.repositories.EndpointRepository;
import cz.restty.app.repositories.LogRepository;
import cz.restty.app.service.EndpointService;
import cz.restty.app.service.ParameterService;
import cz.restty.app.service.ResponseService;
import cz.restty.app.swagger.dto.Path;

/**
 * Default implementation of {@link EndpointService}
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class EndpointServiceImpl implements EndpointService {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private LogRepository logRepository;

    @Override
    public boolean run(Endpoint endpoint) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        endpoint.getHeaders().stream().filter(header -> header.getEnabled()).forEach(endpointHeader -> {
            headers.add(endpointHeader.getHeader().getHeader(), endpointHeader.getHeader().getValue());
        });

        Log log = new Log();
        log.setEndpoint(endpoint);
        log.setRun(LocalDateTime.now());
        log.setResponseStatus(HttpStatus.OK.toString());
        log.setSuccess(true);

        try {
            List<Parameter> pathVariables = endpoint.getParameters()
                    .stream()
                    .filter(pathVariable -> ParamType.PATH.equals(pathVariable.getType()))
                    .collect(Collectors.toList());
            String path = endpoint.getProject().getPath() + endpoint.getPath();
            String resolvedPath = resolvePathVariables(path, pathVariables);
            
            Optional<Parameter> body = endpoint.getParameters()
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

            restTemplate.exchange(resolvedPath, endpoint.getMethod(), entity, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.setSuccess(false);
            log.setResponseStatus((httpClientOrServerExc).getStatusCode().toString());
            log.setResponseMessage(httpClientOrServerExc.getMessage());
        } catch (Exception ex) {
            log.setSuccess(false);
            log.setResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            log.setResponseMessage(ex.getMessage());
        } finally {
            logRepository.save(log);
        }

        return log.getSuccess();
    }

    @Override
    public Endpoint createEndpoint(Project project, Path path) {
        Endpoint endpoint = new Endpoint();
        endpoint.setMethod(path.getMethod());
        endpoint.setPath(path.getPath());
        endpoint.setDescription(path.getSummary());
        endpoint.setProject(project);
        endpointRepository.save(endpoint);

        path.getParameters().forEach(parameter -> {
            parameterService.createParameter(endpoint, parameter).ifPresent(savedParam -> endpoint.addParameter(savedParam));
        });
        
        path.getResponses().forEach(response -> {
            responseService.createResponse(endpoint, response).ifPresent(savedResponse -> endpoint.addResponse(savedResponse));
        });
        
        return endpointRepository.save(endpoint);
    }

    @Override
    public void deleteAllByProject(Project project) {
        endpointRepository.findAllByProject(project).forEach(endpoint -> {
            logRepository.deleteAllByEndpoint(endpoint);
            endpointRepository.delete(endpoint);
        });
    }

    private String resolvePathVariables(String path, List<Parameter> pathVariables) {
        for (Parameter pathVariable : pathVariables) {
            path = path.replaceAll("\\{" + pathVariable.getName() + "\\}", pathVariable.getParameterValue());
        }

        return path;
    }

}