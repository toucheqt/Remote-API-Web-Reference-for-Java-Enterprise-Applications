package com.restty.app.swagger.dto;

import org.springframework.http.HttpMethod;

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

}
