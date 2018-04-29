package cz.restty.app.rest.dto;

import java.time.LocalDateTime;

import cz.restty.app.entities.Log;

public class LogDto {

    private LocalDateTime run;

    private String responseStatus;
    private String responseMessage;

    private Boolean success;

    public LogDto(Log log) {
        this.run = log.getRun();
        this.responseStatus = log.getResponseStatus();
        this.responseMessage = log.getResponseMessage();
        this.success = log.getSuccess();
    }

    public LocalDateTime getRun() {
        return run;
    }

    public void setRun(LocalDateTime run) {
        this.run = run;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
