package com.restty.app.controllers;

import static com.restty.app.constants.AppConstants.REST_API_PREFIX;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.restty.app.entities.Project;
import com.restty.app.repositories.ProjectRepository;

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

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Finds all projects.
     * 
     * @return list of {@link Project}s
     */
    @GetMapping(PROJECTS_PATH)
    @Transactional(readOnly = true)
    public List<Project> findProjects() {
        return Lists.newArrayList(projectRepository.findAll());
    }

    /**
     * Finds project by given name
     * 
     * @param name
     *            name to search by
     * @return {@link Project}
     */
    @Transactional(readOnly = true)
    @GetMapping(PROJECTS_PATH + "/{name}")
    public Project findByName(@PathVariable String name) {
        return projectRepository.getByName(name);
    }

}
