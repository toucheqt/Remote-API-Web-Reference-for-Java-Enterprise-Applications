package com.restty.app.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.restty.app.dto.ProjectDto;
import com.restty.app.entities.Project;
import com.restty.app.exceptions.SwaggerFileUnavailableException;
import com.restty.app.repositories.ProjectRepository;
import com.restty.app.service.EndpointService;
import com.restty.app.service.ProjectService;
import com.restty.app.swagger.dto.Path;
import com.restty.app.swagger.dto.SwaggerJson;

/**
 * Default implementation of {@link ProjectService}.
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private EndpointService endpointService;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectDto createProject(ProjectDto projectDto) throws RestClientException, IOException {
        SwaggerJson json = parseSource(projectDto.getSource());
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setSource(projectDto.getSource());
        project.setPath(json.getBasePath());
        project = projectRepository.save(project);

        for (Path path : json.getPaths()) {
            project.addEndpoint(endpointService.createEndpoint(project, path));
        }

        ProjectDto f = new ProjectDto(projectRepository.save(project));
        return f;
    }

    @Override
    public Project renameProject(Long projectId, String name) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Project [ID=%d] does not exist.", projectId)));
        project.setName(name);
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    private SwaggerJson parseSource(String source) throws IOException, RestClientException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(source, String.class);
        if (response == null || !HttpStatus.OK.equals(response.getStatusCode())) {
            throw new SwaggerFileUnavailableException("Swagger file can not be obtained.");
        }

        return new SwaggerJson(response.getBody());
    }

}
