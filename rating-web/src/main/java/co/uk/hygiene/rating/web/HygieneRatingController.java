package co.uk.hygiene.rating.web;

import co.uk.hygiene.rating.core.dto.Authorities;
import co.uk.hygiene.rating.core.dto.Establishments;
import co.uk.hygiene.rating.service.AuthoritiesService;
import co.uk.hygiene.rating.service.EstablishmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.apache.log4j.Logger;


@RestController
public class HygieneRatingController {

    private static final Logger LOG = Logger.getLogger(HygieneRatingController.class);
    private static final String RATING_JSP_URL = "/rating.html";
    private static final String RATING_VIEW = "rating";
    private static final String RETRIEVE_LOCAL_AUTHORITIES = "/localAuthoritiesList.json";
    private static final String RETRIEVE_ESTABLISHMENTS = "/establishments.json";

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private EstablishmentsService establishmentsService;

    @RequestMapping(value = RATING_JSP_URL)
    public ModelAndView showRatingPage() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Enter: showRatingPage()");
        }

        ModelAndView modelAndView = new ModelAndView(RATING_VIEW);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Exit: showRatingPage()");
        }

        return modelAndView;
    }

    @RequestMapping(value = RETRIEVE_LOCAL_AUTHORITIES, method = RequestMethod.GET)
    public Authorities retrieveLocalAuthorities() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Enter: retrieveLocalAuthorities()");
        }

        Authorities localAuthorities = authoritiesService.getAllAuthorities();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Exit: retrieveLocalAuthorities()");
        }

        return localAuthorities;
    }

    @RequestMapping(value = RETRIEVE_ESTABLISHMENTS, method = RequestMethod.GET)
    public Establishments retrieveEstablishments(@RequestParam("authorityId") String authorityId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Enter: retrieveEstablishments()");
        }

        Establishments establishmentsInAuthority = establishmentsService.getEstablishmentsByAuthorityId(authorityId);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Exit: retrieveEstablishments()");
        }

        return establishmentsInAuthority;
    }
}
