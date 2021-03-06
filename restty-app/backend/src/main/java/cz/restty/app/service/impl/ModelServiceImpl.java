package cz.restty.app.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cz.restty.app.entities.Attribute;
import cz.restty.app.entities.Model;
import cz.restty.app.entities.Project;
import cz.restty.app.repositories.AttributeRepository;
import cz.restty.app.repositories.ModelRepository;
import cz.restty.app.repositories.ParameterRepository;
import cz.restty.app.repositories.ResponseRepository;
import cz.restty.app.rest.dto.ModelDto;
import cz.restty.app.service.AttributeService;
import cz.restty.app.service.ModelService;
import cz.restty.app.utils.JsonUtils;

/**
 * Default implementation of {@link ModelService}
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class ModelServiceImpl implements ModelService {

    private static final Logger logger = Logger.getLogger(ModelServiceImpl.class);

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Override
    public Model createModel(Project project, ModelDto modelDto) {
        Model model = new Model();
        model.setName(modelDto.getName());
        model.setProject(project);
        modelRepository.save(model);
        
        Set<Attribute> attributes = modelDto.getAttributes()
            .stream()
            .map(attr -> attributeService.createAttribute(model, attr))
            .collect(Collectors.toSet());
        
        model.setAttributes(attributes);
        return modelRepository.save(model);
    }

    @Override
    public Model updateModelValues(Model model, String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(content);

            model.getAttributes().forEach(attribute -> {
                attribute.setValue(JsonUtils.getPathValue(rootNode, attribute.getName(), false));
                attributeRepository.save(attribute);
            });
        } catch (Exception ex) {
            logger.error("Error while parsing the Swagger's JSON file", ex);
        }

        return modelRepository.save(model);
    }

    @Override
    public void deleteAllByProject(Project project) {
        modelRepository.findAllByProject(project).forEach(model -> {
            attributeRepository.removeAllByModel(model);
            parameterRepository.deleteAllByModel(model);
            responseRepository.deleteAllByModel(model);
            modelRepository.delete(model);
        });
    }

}
