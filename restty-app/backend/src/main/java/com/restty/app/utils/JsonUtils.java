package com.restty.app.utils;

import java.io.IOException;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.restty.app.exceptions.InvalidSwaggerFileException;

/**
 * Utility class for handling JSON.
 * 
 * @author Ondrej Krpec
 *
 */
public class JsonUtils {

    private static final String SCHEMES_PROPERTY = "schemes";

    /**
     * Get scheme from node. Https and http schemes are preferred.
     * 
     * @param rootNode
     *            {@link JsonNode}
     * @return scheme e.g. https, http
     * @throws JsonParseException
     *             If any parsing problem occurs
     * @throws JsonMappingException
     *             If any mapping problem occurs
     * @throws InvalidSwaggerFileException
     *             If the value is required but does not exist in the node then {@link InvalidSwaggerFileException} is thrown.
     * @throws IOException
     *             If any other serialization problem occurs
     */
    public static String getScheme(JsonNode rootNode)
            throws JsonParseException, JsonMappingException, InvalidSwaggerFileException, IOException {
        Set<String> schemes = fromArrayNode(getArrayNode(rootNode, SCHEMES_PROPERTY, true));
        if (CollectionUtils.isEmpty(schemes)) {
            throw new InvalidSwaggerFileException(
                    String.format("Swagger file does not contain all required attributes, scheme is missing."));
        }

        if (schemes.contains("https")) {
            return "https://";
        } else if (schemes.contains("http")) {
            return "http://";
        }

        return schemes.iterator().next() + "://";
    }

    /**
     * Returns path value from the node. If the value is required but does not exist in the node then {@link InvalidSwaggerFileException} is
     * thrown.
     * 
     * @param node
     *            {@link JsonNode}
     * @param path
     *            Path to search for in the node
     * @param required
     *            True if the path is required
     * @return path value from the node
     * @throws InvalidSwaggerFileException
     *             If the value is required but does not exist in the node then {@link InvalidSwaggerFileException} is thrown.
     */
    public static String getPathValue(JsonNode node, String path, boolean required) throws InvalidSwaggerFileException {
        JsonNode pathNode = node.path(path);
        if (pathNode.isMissingNode()) {
            if (required) {
                throw new InvalidSwaggerFileException(
                        String.format("Swagger file does not contain all required attributes, path=%s is missing.", path));
            }

            return null;
        }

        return pathNode.asText();
    }

    /**
     * Returns array node from the given path. If the value is required but does not exist in the node then
     * {@link InvalidSwaggerFileException} is thrown.
     * 
     * @param node
     *            {@link JsonNode}
     * @param path
     *            Path to search for in the node
     * @param required
     *            True if the path is required
     * @return {@link ArrayNode} on the path
     * @throws InvalidSwaggerFileException
     *             If the value is required but does not exist in the node then {@link InvalidSwaggerFileException} is thrown.
     */
    public static ArrayNode getArrayNode(JsonNode node, String path, boolean required) throws InvalidSwaggerFileException {
        JsonNode pathNode = node.path(path);
        if (pathNode.isMissingNode() || !(pathNode instanceof ArrayNode)) {
            if (required) {
                throw new InvalidSwaggerFileException(
                        String.format("Swagger file does not contain all required attributes, path=%s is missing.", path));
            }

            return null;
        }
        
        return (ArrayNode)pathNode;
    }

    /**
     * Returns array node from the given path. If the value is required but does not exist in the node then
     * {@link InvalidSwaggerFileException} is thrown.
     * 
     * @param node
     *            {@link JsonNode}
     * @param path
     *            Path to search for in the node
     * @param required
     *            True if the path is required
     * @return {@link ArrayNode} on the path
     * @throws InvalidSwaggerFileException
     *             If the value is required but does not exist in the node then {@link InvalidSwaggerFileException} is thrown.
     */
    public static ObjectNode getObjectNode(JsonNode node, String path, boolean required) throws InvalidSwaggerFileException {
        JsonNode pathNode = node.path(path);
        if (pathNode.isMissingNode() || !(pathNode instanceof ObjectNode)) {
            if (required) {
                throw new InvalidSwaggerFileException(
                        String.format("Swagger file does not contain all required attributes, path=%s is missing.", path));
            }

            return null;
        }

        return (ObjectNode)pathNode;
    }

    /**
     * Returns content of array node as set of strings.
     * 
     * @param node
     *            {@link ArrayNode}
     * @return {@link Set} of strings
     * @throws JsonParseException
     *             If any parsing problem occurs
     * @throws JsonMappingException
     *             If any mapping problem occurs
     * @throws IOException
     *             If any other serialization problem occurs
     */
    public static Set<String> fromArrayNode(ArrayNode node) throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(node.traverse(), new TypeReference<Set<String>>() {});
    }

    /**
     * Returns content of array node as set of objects of type given type
     * 
     * @param node
     *            {@link ArrayNode}
     * @param clazz
     *            Type that should be returned
     * @return Set of typed objects
     * @throws JsonParseException
     *             If any parsing problem occurs
     * @throws JsonMappingException
     *             If any mapping problem occurs
     * @throws IOException
     *             If any other serialization problem occurs
     */
    public static <T> Set<T> fromArrayNode(ArrayNode node, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(node.traverse(), mapper.getTypeFactory().constructCollectionType(Set.class, clazz));
    }

}
