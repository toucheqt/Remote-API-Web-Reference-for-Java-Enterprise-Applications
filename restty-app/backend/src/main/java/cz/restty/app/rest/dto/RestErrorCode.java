package cz.restty.app.rest.dto;


/**
 * Error codes used in REST API errors.
 * 
 * @author Ondrej Krpec
 *
 */
public enum RestErrorCode {

    PROJECT_NOT_FOUND("project.notFound"),
    PROJECT_NAME_INVALID("project.name.invalid"),
    TEST_CASE_NAME_INVALID("testCase.name.invalid"),

    SWAGGER_FILE_INVALID("swagger.invalid"),
    SWAGGER_FILE_UNAVAILABLE("swagger.unavailable"),
    GENERAL_SERVER_ERROR("error.server.general"),
    GENERAL_VALIDATION_ERROR("error.validation.general");

    private String value;

    private RestErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
