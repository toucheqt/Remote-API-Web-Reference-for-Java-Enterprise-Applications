package cz.restty.app.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Model;
import cz.restty.app.entities.Response;
import cz.restty.app.repositories.ModelRepository;
import cz.restty.app.repositories.ResponseRepository;
import cz.restty.app.rest.dto.ResponseDto;
import cz.restty.app.service.ResponseService;

/**
 * Default implementation of {@link ResponseService}
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class ResponseServiceImpl implements ResponseService {

    private static final Logger logger = LoggerFactory.getLogger(ResponseServiceImpl.class);

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Override
    public Optional<Response> createResponse(Endpoint endpoint, ResponseDto responseDto) {
        Response response = new Response();
        response.setStatus(responseDto.getHttpStatus());
        response.setDescription(responseDto.getDescription());
        
        if (StringUtils.isNotBlank(responseDto.getModelName())) {
            Optional<Model> model = modelRepository.findByNameIgnoreCase(responseDto.getModelName());
            if (!model.isPresent()) {
                logger.warn(String.format("Error creating endpoint's response [ENDPOINT=%s %s], model [NAME=%s] does not exist.",
                        endpoint.getMethod().toString(), endpoint.getPath(), responseDto.getModelName()));
            }

            response.setModel(model.get());
        }
        
        return Optional.of(responseRepository.save(response));
    }

}
