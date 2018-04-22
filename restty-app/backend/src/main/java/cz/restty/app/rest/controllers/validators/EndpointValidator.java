package cz.restty.app.rest.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.repositories.EndpointRepository;
import cz.restty.app.rest.dto.RestErrorCode;
import cz.restty.app.rest.exceptions.ResourceNotFoundException;

/**
 * Validator for {@link Endpoint} entities.
 * 
 * @author Ondrej Krpec
 *
 */
@Component
public class EndpointValidator {

    @Autowired
    private EndpointRepository endpointRepository;

    /**
     * Finds endpoint by given ID.
     * 
     * @param endpointId
     *            ID of endpoint to search by
     * @return {@link Endpoint}
     */
    public Endpoint validate(Long endpointId) {
        return endpointRepository.findById(endpointId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Endpoint [ID=%d] does not exist.", endpointId),
                        RestErrorCode.ENDPOINT_NOT_FOUND));
    }

}
