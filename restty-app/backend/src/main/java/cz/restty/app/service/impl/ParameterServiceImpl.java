package cz.restty.app.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Model;
import cz.restty.app.entities.ParamType;
import cz.restty.app.entities.Parameter;
import cz.restty.app.repositories.ModelRepository;
import cz.restty.app.repositories.ParameterRepository;
import cz.restty.app.rest.dto.ParameterDto;
import cz.restty.app.service.ParameterService;

/**
 * Default implementation of {@link ParameterService}
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class ParameterServiceImpl implements ParameterService {

    private static final Logger logger = LoggerFactory.getLogger(ParameterServiceImpl.class);

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public Optional<Parameter> createParameter(Endpoint endpoint, ParameterDto parameterDto) {
        if ("formData".equals(parameterDto.getIn()) || "header".equals(parameterDto.getIn())) {
            logger.warn(String.format("Restty does not support '%s' parameter formats.", parameterDto.getIn()));
            return Optional.empty();
        }

        Parameter parameter = new Parameter();
        parameter.setName(parameterDto.getName());
        parameter.setType(ParamType.valueOf(parameterDto.getIn().toUpperCase()));
        parameter.setRequired(parameterDto.isRequired());

        Optional<Model> model = modelRepository.findByNameIgnoreCase(parameterDto.getModelName());
        if (!model.isPresent()) {
            logger.error(String.format("Error creating endpoint's parameter [ENDPOINT=%s %s], model [NAME=%s] does not exist.",
                    endpoint.getMethod().toString(), endpoint.getPath(), parameterDto.getModelName()));
            return Optional.empty();
        }

        parameter.setModel(model.get());
        return Optional.of(parameterRepository.save(parameter));
    }

}
