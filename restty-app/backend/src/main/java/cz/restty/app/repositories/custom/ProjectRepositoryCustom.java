package cz.restty.app.repositories.custom;

import java.util.List;

import cz.restty.app.entities.Project;
import cz.restty.app.rest.dto.ProjectDto;
import cz.restty.app.rest.dto.RunStatisticsDto;
import cz.restty.app.rest.dto.StatsDto;

/**
 * Custom repository that declares methods for manipulation with {@link Project} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ProjectRepositoryCustom {

    /**
     * Finds all projects with information about API endpoints and test cases.
     * 
     * @return list of {@link ProjectDto}
     */
    List<ProjectDto> findAllWithStats();

    /**
     * Finds five most recently called APIs or test cases.
     * 
     * @param project
     *            Project to search by
     * @return List of five {@link RunStatisticsDto}
     */
    List<RunStatisticsDto> findRecentRuns(Project project);

    /**
     * Finds endpoints statistics for a given project.
     * 
     * @param projectId
     *            ID of project to search by.
     * @return {@link StatsDto}
     */
    List<RunStatisticsDto> findRecentFailedRuns(Project project);

}
