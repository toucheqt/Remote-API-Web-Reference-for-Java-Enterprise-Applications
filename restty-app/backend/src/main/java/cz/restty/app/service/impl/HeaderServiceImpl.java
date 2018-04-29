package cz.restty.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.EndpointHeader;
import cz.restty.app.entities.Header;
import cz.restty.app.entities.Project;
import cz.restty.app.repositories.EndpointHeaderRepository;
import cz.restty.app.repositories.EndpointRepository;
import cz.restty.app.repositories.HeaderRepository;
import cz.restty.app.rest.dto.HeaderDto;
import cz.restty.app.service.HeaderService;

/**
 * Default implementation of {@link HeaderService}.
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class HeaderServiceImpl implements HeaderService {

    @Autowired
    private HeaderRepository headerRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private EndpointHeaderRepository endpointHeaderRepository;

    @Override
    public Header createGloabalHeader(Project project, HeaderDto headerDto) {
        Header header = new Header();
        header.setHeader(headerDto.getHeader());
        header.setValue(headerDto.getValue());
        header.setGlobal(true);
        headerRepository.save(header);

        endpointRepository.findAllByProject(project).forEach(endpoint -> {
            EndpointHeader endpointHeader = new EndpointHeader();
            endpointHeader.setEndpoint(endpoint);
            endpointHeader.setHeader(header);
            endpointHeaderRepository.save(endpointHeader);
        });

        return header;
    }

    @Override
    public Header createHeader(Endpoint endpoint, HeaderDto headerDto) {
        Header header = new Header();
        header.setHeader(headerDto.getHeader());
        header.setValue(headerDto.getValue());
        header.setGlobal(false);
        headerRepository.save(header);

        EndpointHeader endpointHeader = new EndpointHeader();
        endpointHeader.setHeader(header);
        endpointHeader.setEndpoint(endpoint);
        endpointHeaderRepository.save(endpointHeader);

        return headerRepository.save(header);
    }

    @Override
    public void updateGlobalHeaderStatus(Header header, Endpoint endpoint) {
        endpointHeaderRepository.findByEndpointAndHeader(endpoint, header).ifPresent(endpointHeader -> {
            endpointHeader.setEnabled(!endpointHeader.getEnabled());
            endpointHeaderRepository.save(endpointHeader);
        });
    }

    @Override
    public Header updateHeader(Header header, HeaderDto headerDto) {
        header.setHeader(headerDto.getHeader());
        header.setValue(headerDto.getValue());
        return headerRepository.save(header);
    }

}
