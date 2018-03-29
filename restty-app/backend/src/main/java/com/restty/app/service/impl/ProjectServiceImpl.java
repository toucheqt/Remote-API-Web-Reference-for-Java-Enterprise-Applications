package com.restty.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restty.app.dto.ProjectDto;
import com.restty.app.entities.Project;
import com.restty.app.repositories.ProjectRepository;
import com.restty.app.service.ProjectService;

/**
 * Default implementation of {@link ProjectService}.
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project createProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setSource(projectDto.getSource());

        // TODO nacist informace o endpointech atd ze source

        return projectRepository.save(project);
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

}
