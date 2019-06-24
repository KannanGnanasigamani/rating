package co.uk.hygiene.rating.service.impl;

import co.uk.hygiene.rating.core.dto.Establishments;
import co.uk.hygiene.rating.core.exceptions.FatalException;
import co.uk.hygiene.rating.service.EstablishmentsService;
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
public class EstablishmentsServiceImpl implements EstablishmentsService {

    private static final Logger LOG = Logger.getLogger(EstablishmentsServiceImpl.class);
    public static final String API_VERSION = "x-api-version";

    @Autowired
    private RestService restService;

    @Autowired
    private RestTemplate restTemplate;

    public Establishments getEstablishmentsByAuthorityId(String authorityId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Enter: getEstablishmentsByAuthorityId()");
        }
        Establishments establishmentsInAuthority = null;

        if(authorityId == null) {
            throw new FatalException("Authority id cannot be null");
        }

        //add version header
        HttpHeaders header = new HttpHeaders();
        String apiVersion = restService.getApiVersion();
        HttpEntity httpEntity = restService.setCustomHeader(header, API_VERSION, apiVersion);

        String url = restService.getBaseUrl()+ "/" + restService.getEstablishmentsByAuthorityUrl() +  authorityId + "&pageSize=0";

        try {
            ResponseEntity<Establishments> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Establishments.class);
            if(response != null) {
                establishmentsInAuthority = response.getBody();
            }
        } catch (Exception e) {
            LOG.debug(e.getCause());
            throw new FatalException("Error in retrieving Establishments!");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Exit: getEstablishmentsByAuthorityId()");
        }
        return establishmentsInAuthority;
    }
}
