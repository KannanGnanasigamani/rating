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
public class Authorities {

    private List<Authority> authorities;

    public Authorities(){
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
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
