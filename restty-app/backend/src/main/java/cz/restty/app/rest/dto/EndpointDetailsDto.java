package cz.restty.app.rest.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpMethod;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Log;

/**
 * DTO that contains detailed information about endpoint.
 * 
 * @author Ondrej Krpec
 *
 */
public class EndpointDetailsDto {

    private Long id;

    private String path;
    private HttpMethod method;
    private String description;

    private LocalDateTime lastRun;
    private Boolean lastRunSuccess;
    private String lastRunMessage;

    private List<ParameterJsonDto> parameters;
    private List<ResponseJsonDto> responses;
    private List<Log> logs;

    public EndpointDetailsDto(Endpoint endpoint) {
        this.id = endpoint.getId();
        this.path = endpoint.getPath();
        this.method = endpoint.getMethod();
        this.description = endpoint.getDescription();
        
        if (CollectionUtils.isNotEmpty(endpoint.getLogs())) {
            Log log = endpoint.getLogs()
                .stream()
                .sorted((l1, l2) -> l1.getRun().compareTo(l2.getRun()))
                .findFirst().get();
            this.lastRun = log.getRun();
            this.lastRunSuccess = log.getSuccess();
            this.lastRunMessage = log.getResponseMessage();
        }

        this.parameters = endpoint.getParameters().stream().map(param -> new ParameterJsonDto(param)).collect(Collectors.toList());
        this.responses = endpoint.getResponses()
                .stream()
                .sorted((r1, r2) -> new Integer(r1.getStatus().value()).compareTo(r2.getStatus().value()))
                .map(response -> new ResponseJsonDto(response))
                .collect(Collectors.toList());
        this.logs = endpoint.getLogs().stream().collect(Collectors.toList());
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

    public String getLastRunMessage() {
        return lastRunMessage;
    }

    public void setLastRunMessage(String lastRunMessage) {
        this.lastRunMessage = lastRunMessage;
    }

    public List<ParameterJsonDto> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterJsonDto> parameters) {
        this.parameters = parameters;
    }

    public List<ResponseJsonDto> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseJsonDto> responses) {
        this.responses = responses;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

}
