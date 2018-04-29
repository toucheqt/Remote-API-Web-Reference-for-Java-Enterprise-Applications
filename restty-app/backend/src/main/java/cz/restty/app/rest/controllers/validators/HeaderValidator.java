package cz.restty.app.rest.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.restty.app.entities.Header;
import cz.restty.app.repositories.HeaderRepository;
import cz.restty.app.rest.dto.RestErrorCode;
import cz.restty.app.rest.exceptions.ResourceNotFoundException;

/**
 * Validator for the {@link Header} entities.
 * 
 * @author Ondrej Krpec
 *
 */
@Component
public class HeaderValidator {

    @Autowired
    private HeaderRepository headerRepository;

    /**
     * Validates that header with given ID does exist.
     * 
     * @param headerId
     *            ID of header to search by
     * @return {@link Header}
     * @throws ResourceNotFoundException
     *             If such header does not exist
     */
    public Header validate(Long headerId) throws ResourceNotFoundException {
        return headerRepository.findById(headerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Header [ID=%d] does not exist.", headerId),
                        RestErrorCode.HEADER_NOT_FOUND));
    }

    /**
     * Validates that global header with given ID does exist.
     * 
     * @param headerId
     *            ID to search by
     * @return {@link Header}
     * @throws ResourceNotFoundException
     *             If such header does not exist
     */
    public Header validateGlobal(Long headerId) throws ResourceNotFoundException {
        return headerRepository.findByGlobalTrueAndId(headerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Global header [ID=%d] does not exist", headerId),
                        RestErrorCode.HEADER_NOT_FOUND));
    }

}

