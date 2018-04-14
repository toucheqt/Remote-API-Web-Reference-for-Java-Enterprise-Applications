package cz.restty.app.repositories.custom;

import java.util.List;

import cz.restty.app.entities.Project;
import cz.restty.app.rest.dto.LastRunsDto;
import cz.restty.app.rest.dto.ProjectDto;

/**
 * Custom repository that declares methods for manipulation with {@link Project} entities.
 * 
 * @author Ondrej Krpec
 *
 */
public interface ProjectRepositoryCustom {

    /**
     * Finds all projects with information about amount of endpoints and test cases.
     * 
     * @return list of {@link ProjectDto}
     */
    List<ProjectDto> findAllWithStats();

    /**
     * Finds last 5 failed api or test case runs.
     * 
     * @param projectId
     *            ID of project to search by
     * @return List of {@link LastRunsDto}
     */
    List<LastRunsDto> findLastRunFailures(Long projectId);

    /**
     * Finds last 5 api or test case runs.
     * 
     * @param projectId
     *            ID of project to search by
     * @return List of {@link LastRunsDto}
     */
    List<LastRunsDto> findRecentRuns(Long projectId);

}
