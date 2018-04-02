package com.restty.app.dto;

/**
 * DTO that contains statistics about endpoints/test cases successes/failures
 * 
 * @author Ondrej Krpec
 *
 */
public class StatsDto {

    private long untested;
    private long successes;
    private long failures;

    // the constructor is used by reflection in EndpointRepository
    public StatsDto(long untested, long successes, long failures) {
        this.untested = untested;
        this.successes = successes;
        this.failures = failures;
    }

    public long getUntested() {
        return untested;
    }

    public void setUntested(long untested) {
        this.untested = untested;
    }

    public long getSuccesses() {
        return successes;
    }

    public void setSuccesses(long successes) {
        this.successes = successes;
    }

    public long getFailures() {
        return failures;
    }

    public void setFailures(long failures) {
        this.failures = failures;
    }

}
