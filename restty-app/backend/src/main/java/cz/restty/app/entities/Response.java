package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.RESPONSE_SEQUENCE;
import static cz.restty.app.constants.DbConstants.RESPONSE_TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import org.springframework.http.HttpStatus;

/**
 * Entity that contains information about endpoints' responses.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = RESPONSE_TABLE)
@SequenceGenerator(name = RESPONSE_SEQUENCE, sequenceName = RESPONSE_SEQUENCE, initialValue = 20, allocationSize = 1)
public class Response {

    private Long id;

    private HttpStatus status;
    private String description;

    private Model model;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = RESPONSE_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Column(name = "description", columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_model", foreignKey = @ForeignKey(name = "id_response_model_fkey"))
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
