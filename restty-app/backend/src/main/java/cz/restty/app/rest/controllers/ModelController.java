package cz.restty.app.rest.controllers;

import static cz.restty.app.constants.AppConstants.REST_API_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cz.restty.app.rest.controllers.validators.ModelValidator;
import cz.restty.app.rest.dto.ModelJsonDto;
import cz.restty.app.service.ModelService;

/**
 * Controller that handles all /model requests.
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class ModelController {

    public static final String MODELS_PATH = REST_API_PREFIX + "/models";
    public static final String MODEL_PATH = MODELS_PATH + "/{modelId}";

    @Autowired
    private ModelService modelService;

    @Autowired
    private ModelValidator modelValidator;

    /**
     * Updates model values.
     * 
     * @param modelId
     *            ID of model to update
     * @param content
     *            content
     * @return {@link ModelJsonDto}
     */
    @PutMapping(MODEL_PATH)
    public ModelJsonDto updateModelValues(@PathVariable Long modelId, @RequestBody String content) {
        return new ModelJsonDto(modelService.updateModelValues(modelValidator.validate(modelId), content));
    }

}
