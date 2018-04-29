package cz.restty.app.rest.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpMethod;

import cz.restty.app.enums.TestType;

/**
 * DTO that contains information about recent API / Test case runs.
 * 
 * @author Ondrej Krpec
 *
 */
public class RunStatisticsDto extends IdNameDto {

    private HttpMethod method;
    private String testType;

    private LocalDateTime run;
    private Boolean success;

    public RunStatisticsDto(Long id, String name, String method, String testType, LocalDateTime run, Boolean success) {
        super(id, name);
        this.testType = TestType.valueOf(testType).getName();
        this.run = run;
        this.success = success;

        if (method != null) {
            this.method = HttpMethod.valueOf(method);
        }
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

    public LocalDateTime getRun() {
        return run;
    }

    public void setRun(LocalDateTime run) {
        this.run = run;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
