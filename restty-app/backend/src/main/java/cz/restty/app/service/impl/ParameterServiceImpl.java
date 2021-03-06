package cz.restty.app.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Model;
import cz.restty.app.entities.Parameter;
import cz.restty.app.enums.ParamType;
import cz.restty.app.repositories.ModelRepository;
import cz.restty.app.repositories.ParameterRepository;
import cz.restty.app.rest.dto.ParameterDto;
import cz.restty.app.rest.dto.ParameterJsonDto;
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

        if (StringUtils.isNotBlank(parameterDto.getModelName())) {
            Optional<Model> model = modelRepository.findByProjectAndNameIgnoreCase(endpoint.getProject(), parameterDto.getModelName());
            if (!model.isPresent()) {
                logger.warn(String.format("Error creating endpoint's parameter [ENDPOINT=%s %s], model [NAME=%s] does not exist.",
                        endpoint.getMethod().toString(), endpoint.getPath(), parameterDto.getModelName()));
            }

            parameter.setModel(model.get());
        } else if (StringUtils.isNotBlank(parameterDto.getParameter())) {
            parameter.setParameter(parameterDto.getParameter());
        }

        return Optional.of(parameterRepository.save(parameter));
    }

    @Override
    public Parameter cloneParameter(Parameter parameter) {
        Parameter clone = new Parameter();
        clone.setName(parameter.getName());
        clone.setType(parameter.getType());
        clone.setRequired(parameter.getRequired());
        clone.setModel(parameter.getModel());
        clone.setParameter(parameter.getParameter());
        clone.setParameterValue(parameter.getParameterValue());
        return parameterRepository.save(clone);
    }

    @Override
    public Parameter updateParameter(Parameter parameter, ParameterJsonDto parameterDto) {
        parameter.setParameterValue(parameterDto.getParameterValue());
        return parameterRepository.save(parameter);
    }

}
