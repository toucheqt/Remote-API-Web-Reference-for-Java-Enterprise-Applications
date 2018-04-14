package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.TEST_CASE_ID_SEQUENCE;
import static cz.restty.app.constants.DbConstants.TEST_CASE_TABLE;

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

/**
 * Entity that contains information about project's test cases.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = TEST_CASE_TABLE)
@SequenceGenerator(name = TEST_CASE_ID_SEQUENCE, sequenceName = TEST_CASE_ID_SEQUENCE, initialValue = 20, allocationSize = 1)
public class TestCase {

    private Long id;

    private String name;
    private String description;

    private LocalDateTime lastRun;
    private Boolean lastRunSuccess;

    private Project project;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TEST_CASE_ID_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "last_run")
    public LocalDateTime getLastRun() {
        return lastRun;
    }


    public void setLastRun(LocalDateTime lastRun) {
        this.lastRun = lastRun;
    }

    @Column(name = "last_run_success")
    public Boolean getLastRunSuccess() {
        return lastRunSuccess;
    }

    public void setLastRunSuccess(Boolean lastRunSuccess) {
        this.lastRunSuccess = lastRunSuccess;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project", nullable = false, foreignKey = @ForeignKey(name = "id_project_fkey"))
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
