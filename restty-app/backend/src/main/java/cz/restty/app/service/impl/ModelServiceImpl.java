package cz.restty.app.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

/**
 * Default implementation of {@link ModelService}
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class ModelServiceImpl implements ModelService {

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
    public void deleteAllByProject(Project project) {
        modelRepository.findAllByProject(project).forEach(model -> {
            attributeRepository.deleteAllByModel(model);
            parameterRepository.deleteAllByModel(model);
            responseRepository.deleteAllByModel(model);
            modelRepository.delete(model);
        });
    }

}
