package co.uk.hygiene.rating.service.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource("classpath:application.properties")
public class RestService {

    @Autowired
    private Environment env;

    public RestService() {
    }

    public String getBaseUrl() {
       return env.getProperty("base.url");
    }

    public String getAuthoritiesUrl() {
        return env.getProperty("authoritiesAll.url");
    }

    public String getEstablishmentsByAuthorityUrl() {
        return env.getProperty("establilshmentsByAuthorityId.url");
    }

    public String getApiVersion() {
        return env.getProperty("apiVersion");
    }

    public HttpEntity setCustomHeader(HttpHeaders header, String customHeader, String value) {
        header.add(customHeader, value );
        return new HttpEntity(header);
    }
}
