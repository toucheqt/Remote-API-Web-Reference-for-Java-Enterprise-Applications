package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.LOG_SEQUENCE;
import static cz.restty.app.constants.DbConstants.LOG_TABLE;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity that contains information about endpoint's and test case's runs.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = LOG_TABLE)
@SequenceGenerator(name = LOG_SEQUENCE, sequenceName = LOG_SEQUENCE, initialValue = 20, allocationSize = 1)
public class Log {

    private Long id;

    private String responseStatus;
    private String responseMessage;

    private LocalDateTime run;
    private Boolean success;

    private Endpoint endpoint;
    private TestCase testCase;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = LOG_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "response_status")
    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Column(name = "response_message")
    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @NotNull
    @Column(name = "run", nullable = false)
    public LocalDateTime getRun() {
        return run;
    }

    public void setRun(LocalDateTime run) {
        this.run = run;
    }

    @NotNull
    @Column(name = "success", nullable = false)
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endpoint", foreignKey = @ForeignKey(name = "id_endpoint_log_fkey"))
    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_test_case", foreignKey = @ForeignKey(name = "id_test_case_log_fkey"))
    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

}
