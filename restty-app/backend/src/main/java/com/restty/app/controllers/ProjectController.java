package com.restty.app.controllers;

import static com.restty.app.constants.AppConstants.REST_API_PREFIX;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.restty.app.entities.Project;

/**
 * Controller that handles all /projects requests.
 * 
 * @author Ondrej Krpec
 *
 */
// @Transactional
@RestController
public class ProjectController {

    private static final String PROJECTS_PATH = REST_API_PREFIX + "/projects";

    /**
     * Finds all projects.
     * 
     * @return list of {@link Project}s
     */
    @GetMapping(PROJECTS_PATH)
    // @Transactional(readOnly = true)
    public List<Project> findProjects() {
        return Lists.newArrayList();
    }

}
