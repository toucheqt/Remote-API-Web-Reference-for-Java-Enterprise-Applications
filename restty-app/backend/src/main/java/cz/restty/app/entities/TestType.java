package cz.restty.app.entities;

/**
 * Test types in the application.
 * 
 * @author Ondrej Krpec
 *
 */
public enum TestType {

    ENDPOINT("Endpoint"),
    TEST_CASE("Test case");

    private String name;

    private TestType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
