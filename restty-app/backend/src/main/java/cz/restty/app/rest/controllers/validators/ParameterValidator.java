package cz.restty.app.rest.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.restty.app.entities.Parameter;
import cz.restty.app.repositories.ParameterRepository;
import cz.restty.app.rest.dto.RestErrorCode;
import cz.restty.app.rest.exceptions.ResourceNotFoundException;

/**
 * Validator for the {@link Parameter} entities.
 * 
 * @author Ondrej Krpec
 *
 */
@Component
public class ParameterValidator {

    @Autowired
    private ParameterRepository parameterRepository;

    /**
     * Validates that parameter with given ID does exist.
     * 
     * @param parameterId
     *            ID of parameter to search by
     * @return {@link Parameter}
     * @throws ResourceNotFoundException
     *             If such parameter does not exist
     */
    public Parameter validate(Long parameterId) throws ResourceNotFoundException {
        return parameterRepository.findById(parameterId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Parameter [ID=%d] does not exist.", parameterId),
                        RestErrorCode.PARAMETER_NOT_FOUND));
    }

}
