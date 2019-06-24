package co.uk.hygiene.rating.web;

import co.uk.hygiene.rating.core.dto.Authorities;
import co.uk.hygiene.rating.core.dto.Authority;
import co.uk.hygiene.rating.core.dto.Establishment;
import co.uk.hygiene.rating.core.dto.Establishments;
import co.uk.hygiene.rating.service.AuthoritiesService;
import co.uk.hygiene.rating.service.EstablishmentsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.given;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author Kannan Gnanasigamani
 *
 */
public class HygieneRatingControllerTest {

    private static final String RATINGS_PAGE = "rating" ;

    @Mock
    private AuthoritiesService mockAuthoritiesService;

    @Mock
    private EstablishmentsService mockEstablishmentsService;

    @InjectMocks
    private HygieneRatingController hygieneRatingController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldShowRatingsPage() throws Exception {
        //when
        ModelAndView modelAndView = hygieneRatingController.showRatingPage();
        //then
        assertEquals(RATINGS_PAGE, modelAndView.getViewName());
    }

    @Test
    public void shouldGetAuthorities() throws Exception {
        //given
        given(mockAuthoritiesService.getAllAuthorities()).willReturn(buildAuthorities());
        //when
        Authorities localAuthorities = hygieneRatingController.retrieveLocalAuthorities();
        //then
        assertNotNull(localAuthorities);
        verify(mockAuthoritiesService).getAllAuthorities();
        verifyNoMoreInteractions(mockAuthoritiesService);
    }

    @Test
    public void shouldGetNoAuthorities() throws Exception {
        //given
        Authorities authorities = new Authorities();

        given(mockAuthoritiesService.getAllAuthorities()).willReturn(authorities);
        //when
        Authorities localAuthorities = hygieneRatingController.retrieveLocalAuthorities();
        //then
        assertNull(localAuthorities.getAuthorities());
        verify(mockAuthoritiesService).getAllAuthorities();
        verifyNoMoreInteractions(mockAuthoritiesService);
    }

    @Test
    public void shouldGetEstablishmentsByAuthorityId() throws Exception {
        //given
        given(mockEstablishmentsService.getEstablishmentsByAuthorityId("100")).willReturn(buildEstablishments());
        //when
        Establishments establishments = hygieneRatingController.retrieveEstablishments("100");
        //then
        assertNotNull(establishments);
        assertEquals (100, establishments.getEstablishments().get(0).getLocalAuthorityId());
        assertEquals ( 1, buildEstablishments().getEstablishments().size());
        assertEquals ( buildEstablishments().getEstablishments().get(0), establishments.getEstablishments().get(0));
        verify(mockEstablishmentsService).getEstablishmentsByAuthorityId("100");
        verifyNoMoreInteractions(mockEstablishmentsService);
    }

    @Test
    public void shouldGetEmptyEstablishmentListByAuthorityId() throws Exception {
        Establishments expectedEstablishments = new Establishments();
        //given
        given(mockEstablishmentsService.getEstablishmentsByAuthorityId("999")).willReturn(expectedEstablishments);
        //when
        Establishments establishments = hygieneRatingController.retrieveEstablishments("999");
        //then
        assertNull(establishments.getEstablishments());
        verify(mockEstablishmentsService).getEstablishmentsByAuthorityId("999");
        verifyNoMoreInteractions(mockEstablishmentsService);
    }


    private Establishments buildEstablishments() {
        Establishments establishments = new Establishments();
        List<Establishment> establishmentList = new ArrayList<Establishment>();
        Establishment establishment = new Establishment();
        establishment.setLocalAuthorityId(100);
        establishmentList.add(establishment);
        establishments.setEstablishments(establishmentList);
        return establishments;
    }

    private Authorities buildAuthorities() {
        Authorities authorities = new Authorities();
        List<Authority> authorityList = new ArrayList<Authority>();
        Collections.addAll(authorityList,
                new Authority(100, "Bradford", "England"));
        authorities.setAuthorities(authorityList);

        return authorities;
    }
}