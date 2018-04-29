package cz.restty.app.rest.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.restty.app.entities.Model;
import cz.restty.app.repositories.ModelRepository;
import cz.restty.app.rest.dto.RestErrorCode;
import cz.restty.app.rest.exceptions.ResourceNotFoundException;

/**
 * Validator for the {@link Model} entities.
 * 
 * @author Ondrej Krpec
 *
 */
@Component
public class ModelValidator {

    @Autowired
    private ModelRepository modelRepository;

    /**
     * Validates that model with given ID does exist.
     * 
     * @param modelId
     *            ID of model to search by
     * @return {@link Model}
     * @throws ResourceNotFoundException
     *             If such model does not exist
     */
    public Model validate(Long modelId) throws ResourceNotFoundException {
        return modelRepository.findById(modelId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Model [ID=%d] does not exist.", modelId),
                        RestErrorCode.MODEL_NOT_FOUND));
    }

}
