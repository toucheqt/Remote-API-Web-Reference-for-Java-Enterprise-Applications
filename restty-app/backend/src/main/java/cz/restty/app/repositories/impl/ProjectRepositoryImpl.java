package cz.restty.app.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cz.restty.app.repositories.custom.ProjectRepositoryCustom;
import cz.restty.app.rest.dto.LastRunsDto;
import cz.restty.app.rest.dto.ProjectDto;

/**
 * Default implementation of {@link ProjectRepositoryCustom}
 * 
 * @author Ondrej Krpec
 *
 */
@Repository
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    @Autowired
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<ProjectDto> findAllWithStats() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT p.id, p.name, p.source, (");
        sql.append("   SELECT count(e.id) FROM endpoint e WHERE e.id_project = p.id ");
        sql.append(" ) AS endpoints, (");
        sql.append("   SELECT count(tc.id) FROM test_case tc WHERE tc.id_project = p.id ");
        sql.append(" ) AS tests ");
        sql.append(" FROM project p ");
        sql.append(" GROUP BY p.id, p.name, p.source ");

        return em.createNativeQuery(sql.toString(), "ProjectStats").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LastRunsDto> findLastRunFailures(Long projectId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM ( ");
        sql.append("   SELECT e.id, e.path AS \"name\", e.method, 'ENDPOINT' AS \"testType\", e.last_run AS \"lastRun\", e.last_run_success AS \"lastRunSuccess\" ");
        sql.append("   FROM endpoint e ");
        sql.append("   WHERE e.id_project = :projectId AND (e.last_run_success IS NULL OR e.last_run_success = false) ");
        sql.append("   UNION ALL ");
        sql.append("   SELECT tc.id, tc.name, NULL AS \"method\", 'TEST_CASE' AS \"testType\", tc.last_run AS \"lastRun\", tc.last_run_success AS \"lastRunSuccess\" ");
        sql.append("   FROM test_case tc ");
        sql.append("   WHERE tc.id_project = :projectId AND (tc.last_run_success IS NULL OR tc.last_run_success = false) ");
        sql.append(" ) AS r ");
        sql.append(" ORDER BY r.\"lastRun\" DESC ");
        sql.append(" LIMIT 5 ");

        Query query = em.createNativeQuery(sql.toString(), "LastRunsStats");
        query.setParameter("projectId", projectId);

        return query.getResultList();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<LastRunsDto> findRecentRuns(Long projectId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM ( ");
        sql.append("   SELECT e.id, e.path AS \"name\", e.method, 'ENDPOINT' AS \"testType\", e.last_run AS \"lastRun\", e.last_run_success AS \"lastRunSuccess\" ");
        sql.append("   FROM endpoint e ");
        sql.append("   WHERE e.id_project = :projectId ");
        sql.append("   UNION ALL ");
        sql.append("   SELECT tc.id, tc.name, NULL AS \"method\", 'TEST_CASE' AS \"testType\", tc.last_run AS \"lastRun\", tc.last_run_success AS \"lastRunSuccess\" ");
        sql.append("   FROM test_case tc ");
        sql.append("   WHERE tc.id_project = :projectId ");
        sql.append(" ) AS r ");
        sql.append(" ORDER BY r.\"lastRun\" DESC ");
        sql.append(" LIMIT 5 ");

        Query query = em.createNativeQuery(sql.toString(), "LastRunsStats");
        query.setParameter("projectId", projectId);

        return query.getResultList();
    }
    

}
