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

    public TestCaseDto() {}

    public TestCaseDto(TestCase testCase) {
        super(testCase.getId(), testCase.getName());
        this.description = testCase.getDescription();

        if (CollectionUtils.isNotEmpty(testCase.getLogs())) {
            Log log = testCase.getLogs()
                .stream()
                .sorted((l1, l2) -> l1.getRun().compareTo(l2.getRun()))
                .findFirst().get();
            this.lastRun = log.getRun();
            this.lastRunSuccess = log.getSuccess();
        }
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
