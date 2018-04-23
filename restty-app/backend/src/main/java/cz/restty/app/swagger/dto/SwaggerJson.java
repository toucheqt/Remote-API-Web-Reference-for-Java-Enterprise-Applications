package cz.restty.app.swagger.dto;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cz.restty.app.rest.dto.AttributeDto;
import cz.restty.app.rest.dto.ModelDto;
import cz.restty.app.rest.dto.ParameterDto;
import cz.restty.app.rest.dto.ResponseDto;
import cz.restty.app.utils.JsonUtils;

/**
 * DTO that contains partial information from swagger's JSON API file.
 * 
 * @author Ondrej Krpec
 *
 */
public class SwaggerJson {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerJson.class);

    private static final String HOST_PROPERTY = "host";
    private static final String BASE_PATH_PROPERTY = "basePath";
    private static final String PATHS_PROPERTY = "paths";
    private static final String SUMMARY_PROPERTY = "summary";
    private static final String RESPONSES_PROPERTY = "responses";
    private static final String DESCRIPTION_PROPERTY = "description";
    private static final String SCHEMA_PROPERTY = "schema";
    private static final String REF_PROPERTY = "$ref";
    private static final String DEFINITIONS_PROPERTY = "definitions";
    private static final String PROPERTIES_PROPERTY = "properties";
    private static final String TYPE_PROPERTY = "type";
    private static final String PARAMETERS_PROPERTY = "parameters";
    private static final String IN_PROPERTY = "in";
    private static final String NAME_PROPERTY = "name";
    private static final String REQUIRED_PROPERTY = "required";

    private String basePath;
    private Set<Path> paths = new HashSet<Path>();
    private Set<ModelDto> models = new HashSet<ModelDto>();

    public SwaggerJson(String source, String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);

        String scheme = JsonUtils.getScheme(rootNode);
        String host = JsonUtils.getPathValue(rootNode, HOST_PROPERTY, false);
        String basePath = Objects.toString(JsonUtils.getPathValue(rootNode, BASE_PATH_PROPERTY, false), "");
        if (StringUtils.isBlank(scheme) || StringUtils.isBlank(host)) {
            this.basePath = source.substring(0, source.lastIndexOf("/") + 1) + basePath;
        } else {
            this.basePath = scheme + host + basePath;
        }

        ObjectNode definitionsNode = JsonUtils.getObjectNode(rootNode, DEFINITIONS_PROPERTY, false);
        if (definitionsNode != null) {
            definitionsNode.fields().forEachRemaining(definitionNode -> {
                ModelDto model = new ModelDto();
                model.setName(definitionNode.getKey());
                
                ObjectNode propertiesNodes = JsonUtils.getObjectNode(definitionNode.getValue(), PROPERTIES_PROPERTY, false);
                if (propertiesNodes != null) {
                    propertiesNodes.fields().forEachRemaining(propertyNode -> {
                        AttributeDto attribute = new AttributeDto();
                        attribute.setName(propertyNode.getKey());

                        String type = JsonUtils.getPathValue(propertyNode.getValue(), TYPE_PROPERTY, false);
                        if (StringUtils.isNotBlank(type)) {
                            attribute.setType(type);
                            model.addAttribute(attribute);
                        } else {
                            String modelName = JsonUtils.getPathValue(propertyNode.getValue(), REF_PROPERTY, false);
                            if (StringUtils.isNotBlank(modelName)) {
                                attribute.setType(modelName.substring(modelName.lastIndexOf('/') + 1));
                                model.addAttribute(attribute);
                            }
                        }
                    });
                }

                models.add(model);
            });
        }

        ObjectNode pathsNode = JsonUtils.getObjectNode(rootNode, PATHS_PROPERTY, true);
        pathsNode.fields().forEachRemaining(pathNode -> {
            String apiPath = pathNode.getKey();
            JsonNode methodsNode = pathNode.getValue();
            methodsNode.fields().forEachRemaining(methodNode -> {
                Path path = new Path();
                path.setPath(apiPath);
                path.setMethod(HttpMethod.valueOf(methodNode.getKey().toUpperCase()));
                path.setSummary(JsonUtils.getPathValue(methodNode.getValue(), SUMMARY_PROPERTY, false));

                if (!methodNode.getValue().path(PARAMETERS_PROPERTY).isMissingNode()) {
                    ArrayNode parametersNode = JsonUtils.getArrayNode(methodNode.getValue(), PARAMETERS_PROPERTY, false);
                    parametersNode.iterator().forEachRemaining(parameterNode -> {
                        ParameterDto parameterDto = new ParameterDto();
                        String in = JsonUtils.getPathValue(parameterNode, IN_PROPERTY, false);
                        if (StringUtils.isNotBlank(in)) {
                            parameterDto.setIn(in);
                        }

                        String name = JsonUtils.getPathValue(parameterNode, NAME_PROPERTY, false);
                        if (StringUtils.isNotBlank(name)) {
                            parameterDto.setName(name);
                        }

                        Boolean required = Boolean.valueOf(JsonUtils.getPathValue(parameterNode, REQUIRED_PROPERTY, false));
                        if (required != null) {
                            parameterDto.setRequired(required);
                        }

                        ObjectNode schemasNode = JsonUtils.getObjectNode(parameterNode, SCHEMA_PROPERTY, false);
                        if (schemasNode != null) {
                            schemasNode.fields().forEachRemaining(schemaNode -> {
                                if (REF_PROPERTY.equals(schemaNode.getKey())) {
                                    if (StringUtils.isNotBlank(schemaNode.getValue().asText())) {
                                        parameterDto.setModelName(schemaNode.getValue().asText()
                                                .substring(schemaNode.getValue().asText().lastIndexOf('/') + 1));
                                    }
                                } else if ("type".equals(schemaNode.getKey())) {
                                    parameterDto.setParameter(schemaNode.getValue().asText());
                                } else {
                                    String modelName = JsonUtils.getPathValue(schemaNode.getValue(), REF_PROPERTY, false);
                                    if (StringUtils.isNotBlank(modelName)) {
                                        parameterDto.setModelName(modelName.substring(modelName.lastIndexOf('/') + 1));
                                    }
                                }
                            });
                        }

                        if (parameterDto.getIn() != null && parameterDto.getName() != null) {
                            path.addParameter(parameterDto);
                        }
                    });

                }
                
                ObjectNode responsesNode = JsonUtils.getObjectNode(methodNode.getValue(), RESPONSES_PROPERTY, false);
                if (responsesNode != null) {
                    responsesNode.fields().forEachRemaining(responseNode -> {
                        try {
                            ResponseDto response = new ResponseDto();
                            String httpStatus = responseNode.getKey();
                            if ("default".equals(httpStatus)) {
                                response.setHttpStatus(HttpStatus.OK);
                            } else {
                                response.setHttpStatus(HttpStatus.valueOf(Integer.valueOf(httpStatus)));
                            }

                            response.setDescription(JsonUtils.getPathValue(responseNode.getValue(), DESCRIPTION_PROPERTY, false));
                            
                            ObjectNode schemasNode = JsonUtils.getObjectNode(responseNode.getValue(), SCHEMA_PROPERTY, false);
                            if (schemasNode != null) {
                                schemasNode.fields().forEachRemaining(schemaNode -> {
                                    if (REF_PROPERTY.equals(schemaNode.getKey())) {
                                        if (StringUtils.isNotBlank(schemaNode.getValue().asText())) {
                                            response.setModelName(schemaNode.getValue().asText()
                                                    .substring(schemaNode.getValue().asText().lastIndexOf('/') + 1));
                                        }
                                    } else {
                                        String modelName = JsonUtils.getPathValue(schemaNode.getValue(), REF_PROPERTY, false);
                                        if (StringUtils.isNotBlank(modelName)) {
                                            response.setModelName(modelName.substring(modelName.lastIndexOf('/') + 1));
                                        }
                                    }
                                });
                            }

                            path.addResponse(response);
                        } catch (Exception ex) {
                            logger.warn(String.format("HTTP Status [STATUS=%s] is incorrect in endpoint [API=%s]", responseNode.getKey(),
                                    apiPath));
                        }
                    });
                }

                paths.add(path);
            });
        });
    }

    public String getBasePath() {
        return basePath;
    }

    public Set<Path> getPaths() {
        return paths;
    }

    public Set<ModelDto> getModels() {
        return models;
    }

}

