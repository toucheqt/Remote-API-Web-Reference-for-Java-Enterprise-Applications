package cz.restty.app.service;

import cz.restty.app.entities.Model;
import cz.restty.app.entities.Project;
import cz.restty.app.rest.dto.ModelDto;

/**
 * Service layer for manipulation with {@link Model} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ModelService {

    /**
     * Creates model for given project.
     * 
     * @param project
     *            {@link Project}
     * @param modelDto
     *            {@link ModelDto}
     * @return {@link Model}
     */
    Model createModel(Project project, ModelDto modelDto);

    /**
     * Updates model values.
     * 
     * @param model
     *            {@link Model} to update
     * @param content
     *            String with content (model values)
     * @return {@link Model}
     */
    Model updateModelValues(Model model, String content);

    /**
     * Deletes all models for given project.
     * 
     * @param project
     *            {@link Project} to delete models for.
     */
    void deleteAllByProject(Project project);
    
}
