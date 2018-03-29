package com.restty.app.controllers;

import static com.restty.app.constants.AppConstants.REST_API_PREFIX;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.restty.app.controllers.validators.ProjectValidator;
import com.restty.app.dto.ProjectDto;
import com.restty.app.entities.Project;
import com.restty.app.repositories.ProjectRepository;
import com.restty.app.service.ProjectService;

/**
 * Controller that handles all /projects requests.
 * 
 * @author Ondrej Krpec
 *
 */
@Transactional
@RestController
public class ProjectController {

    private static final String PROJECTS_PATH = REST_API_PREFIX + "/projects";
    private static final String PROJECT_PATH = PROJECTS_PATH + "/{projectId}";

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectValidator projectValidator;

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
     * Finds project by given id.
     * 
     * @param projectId
     *            ID of project to search by
     * @return {@link Project}
     */
    @GetMapping(PROJECT_PATH)
    @Transactional(readOnly = true)
    public Project findById(@PathVariable Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    /**
     * Finds project by given name
     * 
     * @param name
     *            name to search by
     * @return {@link Project}
     */
    @Transactional(readOnly = true)
    @GetMapping(PROJECTS_PATH + "/validate/{name}")
    public Project findByName(@PathVariable String name) {
        return projectRepository.findByName(name).orElse(null);
    }

    /**
     * Creates project from information in {@link ProjectDto}
     * 
     * @param projectDto
     *            {@link ProjectDto} that contains information about new project.
     * @return {@link Project} and {@link HttpStatus#CREATED}
     */
    @PostMapping(PROJECTS_PATH)
    public ResponseEntity<Project> createProject(@RequestBody @Validated ProjectDto projectDto) {
        projectValidator.validateName(projectDto.getName(), Optional.empty());
        return new ResponseEntity<>(projectService.createProject(projectDto), HttpStatus.CREATED);
    }

    /**
     * Renames project.
     * 
     * @param projectId
     *            ID of project to rename.
     * @param projectDto
     *            DTO that contains new project name
     * @return updated project
     */
    @PutMapping(PROJECT_PATH)
    public Project renameProject(@PathVariable Long projectId, @RequestBody ProjectDto projectDto) {
        projectValidator.validateName(projectDto.getName(), Optional.of(projectId));
        return projectService.renameProject(projectId, projectDto.getName());
    }

    /**
     * Deletes project with given ID.
     * 
     * @param projectId
     *            ID of project to delete
     * @return {@link HttpStatus#NO_CONTENT}
     */
    @DeleteMapping(PROJECT_PATH)
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
