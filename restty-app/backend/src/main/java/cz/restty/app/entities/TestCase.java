package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.TEST_CASE_SEQUENCE;
import static cz.restty.app.constants.DbConstants.TEST_CASE_TABLE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Entity that contains information about project's test cases.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = TEST_CASE_TABLE)
@SequenceGenerator(name = TEST_CASE_SEQUENCE, sequenceName = TEST_CASE_SEQUENCE, initialValue = 20, allocationSize = 1)
public class TestCase {

    private Long id;

    private String name;
    private String description;

    private Project project;

    private Set<TestCaseSettings> settings = new HashSet<>();
    private Set<Log> logs = new HashSet<>();

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TEST_CASE_SEQUENCE)
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project", nullable = false, foreignKey = @ForeignKey(name = "id_project_test_case_fkey"))
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "testCase", cascade = CascadeType.ALL)
    public Set<Log> getLogs() {
        return logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    public Set<TestCaseSettings> getSettings() {
        return settings;
    }

    public void setSettings(Set<TestCaseSettings> settings) {
        this.settings = settings;
    }

    @Transient
    public void addSettings(TestCaseSettings settings) {
        this.settings.add(settings);
    }

}
