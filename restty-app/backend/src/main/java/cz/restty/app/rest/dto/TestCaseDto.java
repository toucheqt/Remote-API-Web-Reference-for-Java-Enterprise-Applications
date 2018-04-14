package cz.restty.app.rest.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import cz.restty.app.entities.TestCase;

/**
 * DTO that contains information about Test Case.
 * 
 * @author Ondrej Krpec
 *
 */
public class TestCaseDto {

    private Long id;

    @NotNull
    private String name;
    private String description;

    private LocalDateTime lastRun;
    private Boolean lastRunSuccess;

    public TestCaseDto() {}

    public TestCaseDto(TestCase testCase) {
        this.id = testCase.getId();
        this.name = testCase.getName();
        this.description = testCase.getDescription();
        this.lastRun = testCase.getLastRun();
        this.lastRunSuccess = testCase.getLastRunSuccess();
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
