package co.uk.hygiene.rating.service.impl;
import co.uk.hygiene.rating.core.dto.Establishment;
import co.uk.hygiene.rating.core.dto.Establishments;
import co.uk.hygiene.rating.core.exceptions.FatalException;
import co.uk.hygiene.rating.service.utils.RestService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Kannan Gnanasigamani
 *
 */
public class EstablishmentsServiceImplTest {

    public static final String API_VERSION = "x-api-version";

    @Mock
    private RestTemplate mockRestTemplate;

    @Mock
    private RestService mockRestService;

    @Mock
    private HttpEntity mockHttpEntity;

    @InjectMocks
    private EstablishmentsServiceImpl establishmentsServiceImpl;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldGetEstablishmentsByAuthority() throws Exception {
        HttpHeaders header = new HttpHeaders();

        //given
        given(mockRestService.getBaseUrl()).willReturn("baseUrl");
        given(mockRestService.getEstablishmentsByAuthorityUrl()).willReturn("Establishments?localAuthorityId=");
        given(mockRestService.getApiVersion()).willReturn("2");
        given(mockRestService.setCustomHeader(header, API_VERSION, "2")).willReturn(mockHttpEntity);

        ResponseEntity<Establishments> response = new ResponseEntity<Establishments>(buildEstablishments(), HttpStatus.OK);

        given(mockRestTemplate.exchange("baseUrl/Establishments?localAuthorityId=100&pageSize=0", HttpMethod.GET, mockHttpEntity, Establishments.class)).willReturn(response);
        //when
        Establishments establishments = establishmentsServiceImpl.getEstablishmentsByAuthorityId("100");
        //then
        assertNotNull(establishments.getEstablishments());
        assertEquals(1, buildEstablishments().getEstablishments().size());
        assertEquals(response.getBody().getEstablishments().get(0), establishments.getEstablishments().get(0));
        assertEquals(100, establishments.getEstablishments().get(0).getLocalAuthorityId());
        assertEquals("Test Business", establishments.getEstablishments().get(0).getBusinessName());
        assertEquals("Pass", establishments.getEstablishments().get(0).getRatingValue());

        verify(mockRestService).getBaseUrl();
        verify(mockRestService).getEstablishmentsByAuthorityUrl();
        verify(mockRestService).getApiVersion();
        verify(mockRestTemplate).exchange("baseUrl/Establishments?localAuthorityId=100&pageSize=0", HttpMethod.GET, mockHttpEntity, Establishments.class);
    }

    @Test
    public void shouldReturnNullIfHeaderIsNullWhileRetrievingEstablishments() {
        //given
        given(mockRestService.getBaseUrl()).willReturn("baseUrl");
        given(mockRestService.getAuthoritiesUrl()).willReturn("Establishments");
        given(mockRestTemplate.exchange("baseUrl/Establishments", HttpMethod.GET, mockHttpEntity, Establishments.class)).willReturn(null);
        //when
        assertNull(establishmentsServiceImpl.getEstablishmentsByAuthorityId("100"));
    }

    @Test
    public void shouldReturnNullIfUrlIsNullWhileRetrievingEstablishments() {
        HttpHeaders header = new HttpHeaders();
        //given
        given(mockRestService.getApiVersion()).willReturn("2");
        given(mockRestService.setCustomHeader(header, API_VERSION, "2")).willReturn(mockHttpEntity);
        given(mockRestTemplate.exchange("baseUrl/Establishments", HttpMethod.GET, mockHttpEntity, Establishments.class)).willReturn(null);
        //when
        assertNull(establishmentsServiceImpl.getEstablishmentsByAuthorityId("100"));
    }

    @Test(expected = FatalException.class)
    public void shouldThrowExceptionWhenBadRequest() {
        HttpHeaders header = new HttpHeaders();
        //given
        given(mockRestService.getBaseUrl()).willReturn("baseUrl");
        given(mockRestService.getEstablishmentsByAuthorityUrl()).willReturn("BadUrl=");
        given(mockRestService.getApiVersion()).willReturn("2");
        given(mockRestService.setCustomHeader(header, API_VERSION, "2")).willReturn(mockHttpEntity);
        given(mockRestTemplate.exchange("baseUrl/BadUrl=100&pageSize=0", HttpMethod.GET, mockHttpEntity, Establishments.class)).willThrow(Exception.class);
        //when
        establishmentsServiceImpl.getEstablishmentsByAuthorityId("100");
    }

    @Test(expected = FatalException.class)
    public void shouldThrowExceptionIfAuthorityIdIsNull() {
        establishmentsServiceImpl.getEstablishmentsByAuthorityId(null);
    }

    private Establishments buildEstablishments() {
        Establishments establishments = new Establishments();
        List<Establishment> establishmentList = new ArrayList<Establishment>();
        Establishment establishment = new Establishment();
        establishment.setLocalAuthorityId(100);
        establishment.setBusinessName("Test Business");
        establishment.setRatingValue("Pass");
        establishmentList.add(establishment);
        establishments.setEstablishments(establishmentList);
        return establishments;
    }
}