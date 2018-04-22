package cz.restty.app.service;

import java.util.Optional;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Response;
import cz.restty.app.rest.dto.ResponseDto;

/**
 * Service layer for manipulation with {@link Response} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ResponseService {

    /**
     * Creates response for given endpoint.
     * 
     * @param endpoint
     *            {@link Endpoint}
     * @param responseDto
     *            {@link ResponseDto}
     * @return {@link Response}
     */
    Optional<Response> createResponse(Endpoint endpoint, ResponseDto responseDto);

}
