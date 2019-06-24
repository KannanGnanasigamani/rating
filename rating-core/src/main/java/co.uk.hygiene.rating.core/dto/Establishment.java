package co.uk.hygiene.rating.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
/**
 * @author Kannan Gnanasigamani
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Establishment {

    @JsonProperty("LocalAuthorityId")
    private int localAuthorityId;
    @JsonProperty("RatingValue")
    private String ratingValue;
    @JsonProperty("BusinessName")
    private String businessName;

    public Establishment() {
    }

    public Establishment(int localAuthorityId, String ratingValue, String businessName) {
        this.localAuthorityId = localAuthorityId;
        this.ratingValue = ratingValue;
        this.businessName = businessName;
    }

    public int getLocalAuthorityId() {
        return localAuthorityId;
    }
    public void setLocalAuthorityId(int localAuthorityId) {
        this.localAuthorityId = localAuthorityId;
    }
    public String getRatingValue() {
        return ratingValue;
    }
    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }
    public String getBusinessName() {
        return businessName;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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
