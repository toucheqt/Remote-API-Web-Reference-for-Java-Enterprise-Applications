package cz.restty.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.restty.app.entities.Project;
import cz.restty.app.entities.TestCase;
import cz.restty.app.repositories.TestCaseRepository;
import cz.restty.app.rest.dto.TestCaseDto;
import cz.restty.app.service.TestCaseService;

/**
 * Default implementation of {@link TestCaseService}.
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Override
    public TestCaseDto createTestCase(Project project, TestCaseDto testCaseDto) {
        TestCase testCase = new TestCase();
        testCase.setName(testCaseDto.getName());
        testCase.setDescription(testCaseDto.getDescription());
        testCase.setProject(project);
        return new TestCaseDto(testCaseRepository.save(testCase));
    }

}
