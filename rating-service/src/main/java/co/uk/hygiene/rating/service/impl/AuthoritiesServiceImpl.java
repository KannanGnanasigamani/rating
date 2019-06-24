package co.uk.hygiene.rating.service.impl;

import co.uk.hygiene.rating.core.dto.Authorities;
import co.uk.hygiene.rating.core.exceptions.FatalException;
import co.uk.hygiene.rating.service.AuthoritiesService;
import co.uk.hygiene.rating.service.utils.RestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Kannan Gnanasigamani
 *
 */
@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

    private static final Logger LOG = Logger.getLogger(AuthoritiesServiceImpl.class);
    public static final String API_VERSION = "x-api-version";

    @Autowired
    private RestService restService;

    @Autowired
    private RestTemplate restTemplate;

    public Authorities getAllAuthorities() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Enter: getAllAuthorities()");
        }
        Authorities localAuthorities = null;

        //add version header
        HttpHeaders header = new HttpHeaders();
        String apiVersion = restService.getApiVersion();
        HttpEntity httpEntity = restService.setCustomHeader(header, API_VERSION, apiVersion);

        String url = restService.getBaseUrl()+ "/" + restService.getAuthoritiesUrl();
        try {
            ResponseEntity<Authorities> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Authorities.class);
            if(response != null) {
                localAuthorities = response.getBody();
            }
        } catch (Exception e) {
            LOG.debug(e.getCause());
            throw new FatalException("Error in retrieving Authorities!", e.getCause());
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Exit: getAllAuthorities()");
        }
        return localAuthorities;
    }
}
