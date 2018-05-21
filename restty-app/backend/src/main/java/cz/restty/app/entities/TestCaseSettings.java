package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.TEST_CASE_SETTINGS_SEQUENCE;
import static cz.restty.app.constants.DbConstants.TEST_CASE_SETTINGS_TABLE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entity that contains test case's endpoint settings.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = TEST_CASE_SETTINGS_TABLE)
@SequenceGenerator(name = TEST_CASE_SETTINGS_SEQUENCE, sequenceName = TEST_CASE_SETTINGS_SEQUENCE, initialValue = 20, allocationSize = 1)
public class TestCaseSettings {

    private Long id;
    private Integer endpointOrder;

    private TestCase parent;

    private TestCase testCase;
    private Endpoint endpoint;

    private Boolean failed = false;
    private Boolean usePrevious = false;

    private Set<Parameter> parameters = new HashSet<>();

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TEST_CASE_SETTINGS_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "endpoint_order")
    public Integer getEndpointOrder() {
        return endpointOrder;
    }

    public void setEndpointOrder(Integer endpointOrder) {
        this.endpointOrder = endpointOrder;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parent")
    public TestCase getParent() {
        return parent;
    }

    public void setParent(TestCase parent) {
        this.parent = parent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_test_case")
    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endpoint")
    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @NotNull
    @Column(name = "failed", nullable = false)
    public Boolean getFailed() {
        return failed;
    }

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

    @NotNull
    @Column(name = "use_previous", nullable = false)
    public Boolean getUsePrevious() {
        return usePrevious;
    }

    public void setUsePrevious(Boolean usePrevious) {
        this.usePrevious = usePrevious;
    }

    @JoinColumn(name = "id_test_case_settings")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<Parameter> parameters) {
        this.parameters = parameters;
    }

}
