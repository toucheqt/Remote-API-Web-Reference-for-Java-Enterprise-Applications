package cz.restty.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.restty.app.entities.Header;
import cz.restty.app.entities.Project;
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

    @Override
    public Header createHeader(Project project, HeaderDto headerDto) {
        Header header = new Header();
        header.setHeader(headerDto.getHeader());
        header.setValue(headerDto.getValue());
        header.setProject(project);
        return headerRepository.save(header);
    }

}
