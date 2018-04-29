package cz.restty.app.entities;

import static cz.restty.app.constants.DbConstants.ENDPOINT_HEADER_SEQUENCE;
import static cz.restty.app.constants.DbConstants.ENDPOINT_HEADER_TABLE;

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
 * Entity that represents the M:N relationship between {@link Header} and {@link Endpoint}.
 * 
 * @author Ondrej Krpec
 *
 */
@Entity
@Table(name = ENDPOINT_HEADER_TABLE)
@SequenceGenerator(name = ENDPOINT_HEADER_SEQUENCE, sequenceName = ENDPOINT_HEADER_SEQUENCE, initialValue = 20, allocationSize = 1)
public class EndpointHeader {

    private Long id;
    private Boolean enabled = true;

    private Endpoint endpoint;
    private Header header;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ENDPOINT_HEADER_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endpoint", foreignKey = @ForeignKey(name = "id_endpoint_fkey"))
    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_header", foreignKey = @ForeignKey(name = "id_header_fkey"))
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

}
