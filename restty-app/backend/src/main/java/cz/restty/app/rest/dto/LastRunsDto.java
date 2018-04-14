package cz.restty.app.rest.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpMethod;

import cz.restty.app.entities.TestType;

/**
 * DTO that contains information about last runs (endpoints or test cases).
 * 
 * @author Ondrej Krpec
 *
 */
public class LastRunsDto {

    private Long id;
    private String name;

    private HttpMethod method;
    private String testType;

    private LocalDateTime lastRun;
    private Boolean lastRunSuccess;

    public LastRunsDto(Long id, String name, String method, String testType, LocalDateTime lastRun, Boolean lastRunSuccess) {
        this.id = id;
        this.name = name;
        this.testType = TestType.valueOf(testType).getName();
        this.lastRun = lastRun;
        this.lastRunSuccess = lastRunSuccess;

        if (method != null) {
            this.method = HttpMethod.valueOf(method);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
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
