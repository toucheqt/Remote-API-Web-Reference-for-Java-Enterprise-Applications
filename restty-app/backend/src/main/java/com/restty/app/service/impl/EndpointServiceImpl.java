package com.restty.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restty.app.entities.Endpoint;
import com.restty.app.entities.Project;
import com.restty.app.repositories.EndpointRepository;
import com.restty.app.service.EndpointService;
import com.restty.app.swagger.dto.Path;

/**
 * Default implementation of {@link EndpointService}
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class EndpointServiceImpl implements EndpointService {

    @Autowired
    private EndpointRepository endpointRepository;

    @Override
    public Endpoint createEndpoint(Project project, Path path) {
        Endpoint endpoint = new Endpoint();
        endpoint.setMethod(path.getMethod());
        endpoint.setPath(path.getPath());
        endpoint.setDescription(path.getSummary());
        endpoint.setProject(project);
        return endpointRepository.save(endpoint);
    }

}