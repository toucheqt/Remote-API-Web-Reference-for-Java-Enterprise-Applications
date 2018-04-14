package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.PROJECT_SEQUENCE;
import static cz.restty.app.constants.DbConstants.PROJECT_TABLE;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cz.restty.app.rest.dto.LastRunsDto;
import cz.restty.app.rest.dto.ProjectDto;

/**
 * Entity that contains information about project.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = PROJECT_TABLE)
@SequenceGenerator(name = PROJECT_SEQUENCE, sequenceName = PROJECT_SEQUENCE, initialValue = 20, allocationSize = 1)
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "ProjectStats", classes = {
            @ConstructorResult(targetClass = ProjectDto.class, columns = { 
                    @ColumnResult(name = "id", type = Long.class), 
                    @ColumnResult(name = "name", type = String.class),
                    @ColumnResult(name = "source", type = String.class), 
                    @ColumnResult(name = "endpoints", type = Long.class), 
                    @ColumnResult(name = "tests", type = Long.class) 
            })
    }),
    @SqlResultSetMapping(name = "LastRunsStats", classes = {
            @ConstructorResult(targetClass = LastRunsDto.class, columns = {
                    @ColumnResult(name = "id", type = Long.class),
                    @ColumnResult(name = "name", type = String.class),
                    @ColumnResult(name = "method", type = String.class),
                    @ColumnResult(name = "testType", type = String.class),
                    @ColumnResult(name = "lastRun", type = LocalDateTime.class),
                    @ColumnResult(name = "lastRunSuccess", type = Boolean.class)
            })
    })
})
public class Project {

    private Long id;

    private String name;
    private String source;

    private String path;

    private Set<Endpoint> endpoints = new HashSet<Endpoint>();
    private Set<TestCase> testCases = new HashSet<TestCase>();

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PROJECT_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Column(name = "source", nullable = false)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @NotNull
    @Column(name = "path", nullable = false)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    public Set<Endpoint> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Set<Endpoint> endpoints) {
        this.endpoints = endpoints;
    }

    @Transient
    public void addEndpoint(Endpoint endpoint) {
        endpoints.add(endpoint);
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    public Set<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(Set<TestCase> testCases) {
        this.testCases = testCases;
    }

}
