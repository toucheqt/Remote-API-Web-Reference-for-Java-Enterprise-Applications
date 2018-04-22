package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.ENDPOINT_SEQUENCE;
import static cz.restty.app.constants.DbConstants.ENDPOINT_TABLE;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpMethod;

/**
 * Entity that contains information about project's endpoints.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = ENDPOINT_TABLE)
@SequenceGenerator(name = ENDPOINT_SEQUENCE, sequenceName = ENDPOINT_SEQUENCE, initialValue = 20, allocationSize = 1)
public class Endpoint {

    private Long id;

    private String path;
    private HttpMethod method;
    private String description;

    private Set<Parameter> parameters = new HashSet<>();
    private Set<Response> responses = new HashSet<>();
    private Set<Log> logs = new HashSet<>();

    private LocalDateTime lastRun;
    private Boolean lastRunSuccess;

    private Project project;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ENDPOINT_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "path", nullable = false)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    @Column(name = "description", columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "last_run")
    public LocalDateTime getLastRun() {
        return lastRun;
    }

    public void setLastRun(LocalDateTime lastRun) {
        this.lastRun = lastRun;
    }

    @Column(name = "last_run_success")
    public Boolean getLastRunSuccess() {
        return lastRunSuccess;
    }

    public void setLastRunSuccess(Boolean lastRunSuccess) {
        this.lastRunSuccess = lastRunSuccess;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project", nullable = false, foreignKey = @ForeignKey(name = "id_project_endpoint_fkey"))
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @JoinColumn(name = "id_endpoint")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Transient
    public void addParameter(Parameter parameter) {
        parameters.add(parameter);
    }

    @JoinColumn(name = "id_endpoint")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Response> getResponses() {
        return responses;
    }

    public void setResponses(Set<Response> responses) {
        this.responses = responses;
    }

    @Transient
    public void addResponse(Response response) {
        responses.add(response);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "endpoint", cascade = CascadeType.ALL)
    public Set<Log> getLogs() {
        return logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    public void addLog(Log log) {
        logs.add(log);
    }

}
