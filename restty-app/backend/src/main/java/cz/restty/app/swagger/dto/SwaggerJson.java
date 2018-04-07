package cz.restty.app.swagger.dto;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cz.restty.app.utils.JsonUtils;

/**
 * DTO that contains partial information from swagger's JSON API file.
 * 
 * @author Ondrej Krpec
 *
 */
public class SwaggerJson {

    private static final String HOST_PROPERTY = "host";
    private static final String BASE_PATH_PROPERTY = "basePath";
    private static final String PATHS_PROPERTY = "paths";
    private static final String SUMMARY_PROPERTY = "summary";

    private String basePath;
    private Set<Path> paths = new HashSet<Path>();

    public SwaggerJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);

        String scheme = JsonUtils.getScheme(rootNode);
        String host = JsonUtils.getPathValue(rootNode, HOST_PROPERTY, true);
        String basePath = Objects.toString(JsonUtils.getPathValue(rootNode, BASE_PATH_PROPERTY, false), "");
        this.basePath = scheme + host + basePath;

        ObjectNode pathsNode = JsonUtils.getObjectNode(rootNode, PATHS_PROPERTY, true);
        pathsNode.fields().forEachRemaining(pathNode -> {
            String apiPath = pathNode.getKey();
            JsonNode methodsNode = pathNode.getValue();
            methodsNode.fields().forEachRemaining(methodNode -> {
                Path path = new Path();
                path.setPath(apiPath);
                path.setMethod(HttpMethod.valueOf(methodNode.getKey().toUpperCase()));
                path.setSummary(JsonUtils.getPathValue(methodNode.getValue(), SUMMARY_PROPERTY, false));
                paths.add(path);
            });
        });

        // FIXME remove
        // ukazka jak nacist data ze tridy
        if (!rootNode.path("tags").isMissingNode()) {
            JsonUtils.fromArrayNode(JsonUtils.getArrayNode(rootNode, "tags", false), Tag.class);
        }
    }

    public String getBasePath() {
        return basePath;
    }

    public Set<Path> getPaths() {
        return paths;
    }

}

