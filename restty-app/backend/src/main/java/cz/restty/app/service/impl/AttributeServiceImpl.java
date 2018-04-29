package cz.restty.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.restty.app.entities.Attribute;
import cz.restty.app.entities.Model;
import cz.restty.app.repositories.AttributeRepository;
import cz.restty.app.rest.dto.AttributeDto;
import cz.restty.app.service.AttributeService;

/**
 * Default implementation of {@link AttributeService}.
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public Attribute createAttribute(Model model, AttributeDto attributeDto) {
        Attribute attribute = new Attribute();
        attribute.setName(attributeDto.getName());
        attribute.setType(attributeDto.getType());
        attribute.setModel(model);
        return attributeRepository.save(attribute);
    }

}
