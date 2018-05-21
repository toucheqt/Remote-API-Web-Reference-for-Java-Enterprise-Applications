package cz.restty.app.rest.dto;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import cz.restty.app.entities.TestCaseSettings;

/**
 * DTO that contains information about Test Case Settings.
 * 
 * @author Ondrej Krpec
 *
 */
public class TestCaseSettingsDto {

    private Long id;
    private Integer endpointOrder;

    private Boolean failed;

    private TestCaseDto parent;

    private EndpointDetailsDto endpoint;
    private Set<ParameterJsonDto> parameters;

    public TestCaseSettingsDto(TestCaseSettings settings) {
        if (settings != null) {
            this.id = settings.getId();
            this.endpointOrder = settings.getEndpointOrder();
            this.endpoint = new EndpointDetailsDto(settings.getEndpoint());
            this.failed = settings.getFailed();
            this.parent = new TestCaseDto(settings.getParent());
            if (CollectionUtils.isNotEmpty(settings.getParameters())) {
                this.parameters = settings.getParameters().stream().map(p -> new ParameterJsonDto(p)).collect(Collectors.toSet());
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEndpointOrder() {
        return endpointOrder;
    }

    public void setEndpointOrder(Integer endpointOrder) {
        this.endpointOrder = endpointOrder;
    }

    public EndpointDetailsDto getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(EndpointDetailsDto endpoint) {
        this.endpoint = endpoint;
    }

    public Boolean getFailed() {
        return failed;
    }

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

    public Set<ParameterJsonDto> getParameters() {
        return parameters;
    }

    public void setParameters(Set<ParameterJsonDto> parameters) {
        this.parameters = parameters;
    }

    public TestCaseDto getParent() {
        return parent;
    }

    public void setParent(TestCaseDto parent) {
        this.parent = parent;
    }

}
