package cz.restty.app.service;

import cz.restty.app.entities.Attribute;
import cz.restty.app.entities.Model;
import cz.restty.app.rest.dto.AttributeDto;

/**
 * Service layer for manipulation with {@link Attribute} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface AttributeService {

    /**
     * Creates attribute for given model.
     * 
     * @param model
     *            {@link Model}
     * @param attributeDto
     *            {@link AttributeDto}
     * @return {@link Attribute}
     */
    Attribute createAttribute(Model model, AttributeDto attributeDto);

}
