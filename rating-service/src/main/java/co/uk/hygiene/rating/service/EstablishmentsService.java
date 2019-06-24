package co.uk.hygiene.rating.service;

import co.uk.hygiene.rating.core.dto.Establishments;

/**
 * @author Kannan Gnanasigamani
 *
 */
public interface EstablishmentsService {
    Establishments getEstablishmentsByAuthorityId(String authorityId);
}
