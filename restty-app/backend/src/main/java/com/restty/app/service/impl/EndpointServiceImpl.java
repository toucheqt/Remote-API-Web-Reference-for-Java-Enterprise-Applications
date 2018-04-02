package com.restty.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restty.app.repositories.EndpointRepository;
import com.restty.app.service.EndpointService;

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

}