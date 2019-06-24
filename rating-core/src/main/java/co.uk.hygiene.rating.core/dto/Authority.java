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
public class Authority {

    @JsonProperty("LocalAuthorityId")
    private int localAuthorityId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("RegionName")
    private String regionName;

    public Authority(){
    }

    public Authority(int localAuthorityId, String name, String regionName){
        this.localAuthorityId = localAuthorityId;
        this.name = name;
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getLocalAuthorityId() {
        return localAuthorityId;
    }
    public void setLocalAuthorityId(int localAuthorityId) {
        this.localAuthorityId = localAuthorityId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

