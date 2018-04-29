package cz.restty.app.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cz.restty.app.entities.Project;
import cz.restty.app.repositories.custom.ProjectRepositoryCustom;
import cz.restty.app.rest.dto.ProjectDto;
import cz.restty.app.rest.dto.RunStatisticsDto;

/**
 * Default implementation of {@link ProjectRepositoryCustom} repository.
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
    public List<RunStatisticsDto> findRecentRuns(Project project) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM ( ");
        sql.append("   SELECT e.id, e.path AS \"name\", e.method, 'ENDPOINT' AS \"testType\", l.run, l.success ");
        sql.append("   FROM endpoint e ");
        sql.append("   LEFT JOIN log l ON l.id_endpoint = e.id ");
        sql.append("   WHERE e.id_project = :projectId ");
        sql.append("   AND (l.id IS NULL OR l.id = (SELECT max(l2.id) FROM log l2 WHERE l2.id_endpoint = e.id)) ");
        sql.append("   UNION ALL ");
        sql.append("   SELECT tc.id, tc.name, NULL AS \"method\", 'TEST_CASE' AS \"testType\", l.run, l.success ");
        sql.append("   FROM test_case tc ");
        sql.append("   LEFT JOIN log l ON l.id_test_case = tc.id");
        sql.append("   WHERE tc.id_project = :projectId ");
        sql.append("   AND (l.id IS NULL OR l.id = (SELECT max(l2.id) FROM log l2 WHERE l2.id_test_case = tc.id)) ");
        sql.append(" ) AS r ");
        sql.append(" ORDER BY r.run DESC ");
        sql.append(" LIMIT 5 ");

        Query query = em.createNativeQuery(sql.toString(), "RunStatistics");
        query.setParameter("projectId", project.getId());

        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RunStatisticsDto> findRecentFailedRuns(Project project) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * FROM ( ");
        sql.append("   SELECT e.id, e.path AS \"name\", e.method, 'ENDPOINT' AS \"testType\", l.run, l.success ");
        sql.append("   FROM endpoint e ");
        sql.append("   LEFT JOIN log l ON l.id_endpoint = e.id ");
        sql.append("   WHERE e.id_project = :projectId ");
        sql.append("   AND (l.id IS NULL OR l.id = (SELECT max(l2.id) FROM log l2 WHERE l2.id_endpoint = e.id AND l2.success = false)) ");
        sql.append("   UNION ALL ");
        sql.append("   SELECT tc.id, tc.name, NULL AS \"method\", 'TEST_CASE' AS \"testType\", l.run, l.success ");
        sql.append("   FROM test_case tc ");
        sql.append("   LEFT JOIN log l ON l.id_test_case = tc.id ");
        sql.append("   WHERE tc.id_project = :projectId ");
        sql.append("   AND (l.id IS NULL OR l.id = (SELECT max(l2.id) FROM log l2 WHERE l2.id_test_case = tc.id AND l2.success = false)) ");
        sql.append(" ) AS r ");
        sql.append(" ORDER BY r.run DESC ");
        sql.append(" LIMIT 5 ");

        Query query = em.createNativeQuery(sql.toString(), "RunStatistics");
        query.setParameter("projectId", project.getId());

        return query.getResultList();
    }
    
}
