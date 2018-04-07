package cz.restty.app.rest.dto;

/**
 * DTO that contains statistics about endpoints/test cases successes/failures
 * 
 * @author Ondrej Krpec
 *
 */
public class StatsDto {

    private Long untested;
    private Long successes;
    private Long failures;

    // the constructor is used by reflection in EndpointRepository
    public StatsDto(Long untested, Long successes, Long failures) {
        this.untested = untested;
        this.successes = successes;
        this.failures = failures;
    }

    public Long getUntested() {
        return untested;
    }

    public void setUntested(Long untested) {
        this.untested = untested;
    }

    public Long getSuccesses() {
        return successes;
    }

    public void setSuccesses(Long successes) {
        this.successes = successes;
    }

    public Long getFailures() {
        return failures;
    }

    public void setFailures(Long failures) {
        this.failures = failures;
    }

}
