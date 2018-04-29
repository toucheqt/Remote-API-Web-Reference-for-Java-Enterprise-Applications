package cz.restty.app.rest.controllers;

import static cz.restty.app.constants.AppConstants.REST_API_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cz.restty.app.rest.controllers.validators.ParameterValidator;
import cz.restty.app.rest.dto.ParameterJsonDto;
import cz.restty.app.service.ParameterService;

/**
 * Controller that handles all /parameters requests.
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class ParameterController {

    public static final String PARAMETERS_PATH = REST_API_PREFIX + "/parameters";
    public static final String PARAMETER_PATH = PARAMETERS_PATH + "/{parameterId}";

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private ParameterValidator parameterValidator;

    /**
     * Updates given paramter.
     * 
     * @param parameterId
     *            ID of parameter to update
     * @param parameterDto
     *            DTO that contains updated information
     * @return {@link ParameterJsonDto}
     */
    @PutMapping(PARAMETER_PATH)
    public ParameterJsonDto updateParameter(@PathVariable Long parameterId, @RequestBody ParameterJsonDto parameterDto) {
        return new ParameterJsonDto(parameterService.updateParameter(parameterValidator.validate(parameterId), parameterDto));
    }

}
