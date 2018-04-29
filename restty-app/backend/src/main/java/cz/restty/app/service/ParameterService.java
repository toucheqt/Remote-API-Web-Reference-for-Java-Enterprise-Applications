package cz.restty.app.service;

import java.util.Optional;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Parameter;
import cz.restty.app.rest.dto.ParameterDto;
import cz.restty.app.rest.dto.ParameterJsonDto;

/**
 * Service layer for manipulation with {@link Parameter} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ParameterService {

    /**
     * Creates parameter for given endpoint.
     * 
     * @param endpoint
     *            {@link Endpoint}
     * @param parameterDto
     *            {@link ParameterDto}
     * @return {@link Parameter}
     */
    Optional<Parameter> createParameter(Endpoint endpoint, ParameterDto parameterDto);

    /**
     * Updates given parameter
     * 
     * @param parameter
     *            {@link Parameter}
     * @param parameterDto
     *            {@link ParameterJsonDto}
     * @return {@link Parameter}
     */
    Parameter updateParameter(Parameter parameter, ParameterJsonDto parameterDto);

}
