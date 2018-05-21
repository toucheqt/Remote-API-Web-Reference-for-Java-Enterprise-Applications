package cz.restty.app.rest.dto;

import java.time.LocalDateTime;

import org.apache.commons.collections4.CollectionUtils;

import cz.restty.app.entities.Log;
import cz.restty.app.entities.TestCase;

/**
 * DTO that contains information about Test Case.
 * 
 * @author Ondrej Krpec
 *
 */
public class TestCaseDto extends IdNameDto {

    private String description;

    private LocalDateTime lastRun;
    private Boolean lastRunSuccess;
    private String lastRunMessage;

    private Boolean hasSteps;

    public TestCaseDto() {}

    public TestCaseDto(TestCase testCase) {
        super(testCase.getId(), testCase.getName());
        this.description = testCase.getDescription();

        if (CollectionUtils.isNotEmpty(testCase.getLogs())) {
            Log log = testCase.getLogs()
                .stream()
                    .sorted((l1, l2) -> l1.getRun().compareTo(l2.getRun()) * -1)
                .findFirst().get();
            this.lastRun = log.getRun();
            this.lastRunSuccess = log.getSuccess();
            this.lastRunMessage = log.getResponseMessage();
        }

        this.hasSteps = CollectionUtils.isNotEmpty(testCase.getSettings());
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

    public Boolean getHasSteps() {
        return hasSteps;
    }

    public void setHasSteps(Boolean hasSteps) {
        this.hasSteps = hasSteps;
    }

    public String getLastRunMessage() {
        return lastRunMessage;
    }

    public void setLastRunMessage(String lastRunMessage) {
        this.lastRunMessage = lastRunMessage;
    }

}
