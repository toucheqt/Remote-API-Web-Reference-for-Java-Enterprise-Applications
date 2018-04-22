package cz.restty.app.swagger.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpMethod;

import cz.restty.app.rest.dto.ParameterDto;
import cz.restty.app.rest.dto.ResponseDto;

/**
 * DTO that contains information about paths.
 * 
 * @author Ondrej Krpec
 *
 */
public class Path {

    private String path;
    private HttpMethod method;
    private String summary;

    private Set<ParameterDto> parameters = new HashSet<>();
    private Set<ResponseDto> responses = new HashSet<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Set<ParameterDto> getParameters() {
        return parameters;
    }

    public void setParameters(Set<ParameterDto> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(ParameterDto parameter) {
        parameters.add(parameter);
    }

    public Set<ResponseDto> getResponses() {
        return responses;
    }

    public void setResponses(Set<ResponseDto> responses) {
        this.responses = responses;
    }

    public void addResponse(ResponseDto response) {
        responses.add(response);
    }

}
