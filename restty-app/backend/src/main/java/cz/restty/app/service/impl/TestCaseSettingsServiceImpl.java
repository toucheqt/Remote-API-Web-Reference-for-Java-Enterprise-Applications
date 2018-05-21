package cz.restty.app.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.restty.app.entities.Endpoint;
import cz.restty.app.entities.Parameter;
import cz.restty.app.entities.TestCase;
import cz.restty.app.entities.TestCaseSettings;
import cz.restty.app.repositories.EndpointRepository;
import cz.restty.app.repositories.ParameterRepository;
import cz.restty.app.repositories.TestCaseRepository;
import cz.restty.app.repositories.TestCaseSettingsRepository;
import cz.restty.app.rest.dto.IdNameMethodDto;
import cz.restty.app.service.ParameterService;
import cz.restty.app.service.TestCaseSettingsService;

/**
 * Default implementation of {@link TestCaseSettingsService}.
 * 
 * @author Ondrej Krpec
 *
 */
@Service
public class TestCaseSettingsServiceImpl implements TestCaseSettingsService {

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private TestCaseSettingsRepository testCaseSettingsRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public List<IdNameMethodDto> findAllSteps(TestCase testCase) {
        List<Endpoint> endpoints = endpointRepository.findAllByProject(testCase.getProject());
        // TODO umoznit pridavat i test casy
        // List<TestCase> testCases = testCaseRepository.findAllByProject(testCase.getProject()).stream()
        // .filter(tc -> !tc.getId().equals(testCase.getId())).collect(Collectors.toList());

        List<IdNameMethodDto> steps = new ArrayList<>();
        steps.addAll(endpoints.stream().map(e -> new IdNameMethodDto(e.getId(), e.getPath(), e.getMethod())).collect(Collectors.toList()));
        // steps.addAll(testCases.stream().map(tc -> new IdNameMethodDto(tc.getId(), tc.getName(), null)).collect(Collectors.toList()));
        steps.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));

        return steps;
    }

    @Override
    public TestCaseSettings addEndpoint(TestCase parent, Endpoint step, Boolean usePrevious) {
        TestCaseSettings settings = new TestCaseSettings();
        settings.setParent(parent);
        settings.setEndpoint(step);
        settings.setEndpointOrder(parent.getSettings().size() + 1);
        if (BooleanUtils.isTrue(usePrevious)) {
            settings.setUsePrevious(true);
        }

        Set<Parameter> parameters = new HashSet<>();
        step.getParameters().forEach(param -> {
            parameters.add(parameterService.cloneParameter(param));
        });
        settings.setParameters(parameters);
        
        return testCaseSettingsRepository.save(settings);
    }

    @Override
    public TestCaseSettings addTestCase(TestCase parent, TestCase step) {
        TestCaseSettings settings = new TestCaseSettings();
        settings.setParent(parent);
        settings.setTestCase(step);
        settings.setEndpointOrder(parent.getSettings().size() + 1);
        return testCaseSettingsRepository.save(settings);
    }

    @Override
    public void removeSteps(TestCase testCase, Integer stepOrder) {
        testCaseSettingsRepository.findAllByParent(testCase)
            .stream()
            .filter(settings -> settings.getEndpointOrder() >= stepOrder)
            .forEach(settings -> {
                settings.getParameters().forEach(p -> parameterRepository.delete(p));
                testCaseSettingsRepository.delete(settings);
            });
    }

    @Override
    public void removeAllByTestCase(TestCase testCase) {
        testCaseSettingsRepository.findAllByParent(testCase).forEach(settings -> {
            settings.getParameters().forEach(p -> parameterRepository.delete(p));
            testCaseSettingsRepository.delete(settings);
        });

        testCaseSettingsRepository.findAllByTestCase(testCase).forEach(settings -> {
            settings.getParameters().forEach(p -> parameterRepository.delete(p));
            testCaseSettingsRepository.delete(settings);
        });
    }

}
