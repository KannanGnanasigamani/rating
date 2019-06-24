package co.uk.hygiene.rating.service.impl;

import co.uk.hygiene.rating.core.dto.Authorities;
import co.uk.hygiene.rating.core.dto.Authority;
import co.uk.hygiene.rating.core.exceptions.FatalException;
import co.uk.hygiene.rating.service.utils.RestService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Kannan Gnanasigamani
 *
 */
public class AuthoritiesServiceImplTest {

    public static final String API_VERSION = "x-api-version";

    @Mock
    private RestTemplate mockRestTemplate;

    @Mock
    private RestService mockRestService;

    @Mock
    private HttpEntity mockHttpEntity;

    @InjectMocks
    private AuthoritiesServiceImpl authoritiesServiceImpl;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldGetAllAuthorities() throws Exception {
        HttpHeaders header = new HttpHeaders();

        //given
        given(mockRestService.getBaseUrl()).willReturn("baseUrl");
        given(mockRestService.getAuthoritiesUrl()).willReturn("AuthoritiesAllUrl");
        given(mockRestService.getApiVersion()).willReturn("2");
        given(mockRestService.setCustomHeader(header, API_VERSION, "2")).willReturn(mockHttpEntity);

        ResponseEntity<Authorities> response = new ResponseEntity<Authorities>(buildAuthorities(), HttpStatus.OK);

        given(mockRestTemplate.exchange("baseUrl/AuthoritiesAllUrl", HttpMethod.GET, mockHttpEntity, Authorities.class)).willReturn(response);
        //when
        Authorities authorities = authoritiesServiceImpl.getAllAuthorities();
        //then
        assertNotNull(authorities.getAuthorities());
        assertEquals(3, buildAuthorities().getAuthorities().size());
        assertEquals(response.getBody().getAuthorities().get(0), authorities.getAuthorities().get(0));
        assertEquals(response.getBody().getAuthorities().get(1), authorities.getAuthorities().get(1));
        assertEquals(response.getBody().getAuthorities().get(2), authorities.getAuthorities().get(2));

        verify(mockRestService).getBaseUrl();
        verify(mockRestService).getAuthoritiesUrl();
        verify(mockRestService).getApiVersion();
        verify(mockRestTemplate).exchange("baseUrl/AuthoritiesAllUrl", HttpMethod.GET, mockHttpEntity, Authorities.class);
    }

    @Test
    public void shouldReturnNullIfHeaderIsNullWhileRetrievingAuthorities() {
        //given
        given(mockRestService.getBaseUrl()).willReturn("baseUrl");
        given(mockRestService.getAuthoritiesUrl()).willReturn("AuthoritiesAllUrl");
        given(mockRestTemplate.exchange("baseUrl/AuthoritiesAllUrl", HttpMethod.GET, mockHttpEntity, Authorities.class)).willReturn(null);
        //when
        assertNull(authoritiesServiceImpl.getAllAuthorities());
    }

    @Test
    public void shouldReturnNullIfUrlIsNullWhileRetrievingAuthorities() {
        HttpHeaders header = new HttpHeaders();
        //given
        given(mockRestService.getApiVersion()).willReturn("2");
        given(mockRestService.setCustomHeader(header, API_VERSION, "2")).willReturn(mockHttpEntity);
        given(mockRestTemplate.exchange("baseUrl/AuthoritiesAllUrl", HttpMethod.GET, mockHttpEntity, Authorities.class)).willReturn(null);
        //when
        assertNull(authoritiesServiceImpl.getAllAuthorities());
    }

    @Test(expected = FatalException.class)
    public void shouldThrowExceptionWhenBadRequest() {
        HttpHeaders header = new HttpHeaders();
        //given
        given(mockRestService.getBaseUrl()).willReturn("baseUrl");
        given(mockRestService.getAuthoritiesUrl()).willReturn("BadUrl");
        given(mockRestService.getApiVersion()).willReturn("2");
        given(mockRestService.setCustomHeader(header, API_VERSION, "2")).willReturn(mockHttpEntity);
        given(mockRestTemplate.exchange("baseUrl/BadUrl", HttpMethod.GET, mockHttpEntity, Authorities.class)).willThrow(Exception.class);
        //when
        authoritiesServiceImpl.getAllAuthorities();
    }

    private Authorities  buildAuthorities() {
        Authorities authorities = new Authorities();
        List<Authority> authorityList = new ArrayList<Authority>();
        Collections.addAll(authorityList,
                new Authority(1, "Edinburgh", "Scotland"),
                new Authority(2, "York", "England"),
                new Authority(3, "Bradford", "England"));
        authorities.setAuthorities(authorityList);
        //ResponseEntity<Authorities> response = new ResponseEntity<Authorities>(HttpStatus.OK);
        return authorities;
    }
}