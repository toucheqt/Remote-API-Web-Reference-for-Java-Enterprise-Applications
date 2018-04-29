package cz.restty.app.enums;

/**
 * Parameter types in the application.
 * 
 * @author Ondrej Krpec
 *
 */
public enum ParamType {

    PATH("path"),
    QUERY("query"),
    BODY("body");

    private String name;

    private ParamType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}