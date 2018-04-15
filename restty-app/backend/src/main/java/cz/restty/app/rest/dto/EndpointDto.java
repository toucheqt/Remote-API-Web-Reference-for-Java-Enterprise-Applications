package cz.restty.app.rest.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpMethod;

import cz.restty.app.entities.Endpoint;

/**
 * DTO that contains information about {@link Endpoint}s
 * 
 * @author Ondrej Krpec
 *
 */
public class EndpointDto {

    private Long id;

    private String path;
    private HttpMethod method;
    private String description;

    private LocalDateTime lastRun;
    private Boolean lastRunSuccess;

    public EndpointDto() {}

    public EndpointDto(Endpoint endpoint) {
        this.id = endpoint.getId();
        this.path = endpoint.getPath();
        this.method = endpoint.getMethod();
        this.description = endpoint.getDescription();
        this.lastRun = endpoint.getLastRun();
        this.lastRunSuccess = endpoint.getLastRunSuccess();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLastRun() {
        return lastRun;
    }

    public void setLastRun(LocalDateTime lastRun) {
        this.lastRun = lastRun;
    }

    public Boolean getLastRunSuccess() {
        return lastRunSuccess;
    }

    public void setLastRunSuccess(Boolean lastRunSuccess) {
        this.lastRunSuccess = lastRunSuccess;
    }

}
