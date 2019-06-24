package co.uk.hygiene.rating.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

/**
 * @author Kannan Gnanasigamani
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Establishments {

    private List<Establishment> establishments;

    public Establishments() {
    }

    public List<Establishment> getEstablishments() {
        return establishments;
    }

    public void setEstablishments(List<Establishment> establishments) {
        this.establishments = establishments;
    }

    // Hashcode Implementation
    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    // Equals Imlementation
    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj);
    }

    // return String representation
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,	ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
