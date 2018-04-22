package cz.restty.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Project;
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

}