package com.manager.smbuilding.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manager.smbuilding.infrastructure.configuration.SimpleHttpServletRequest;
import org.springdoc.webmvc.api.OpenApiResource;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

@Service
public class OpenApiDocumentationService {

    private final OpenApiResource openApiResource;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper

    public OpenApiDocumentationService(OpenApiResource openApiResource) {
        this.openApiResource = openApiResource;
        this.objectMapper = new ObjectMapper();
    }

    public void saveOpenApiDocumentation() {
        try {
            // Create a simulated HttpServletRequest
            SimpleHttpServletRequest request = new SimpleHttpServletRequest();

            // Generate JSON from OpenAPI documentation
            byte[] jsonBytes = openApiResource.openapiJson(request, "/v3/api-docs", Locale.getDefault());
            String jsonDocs = new String(jsonBytes, StandardCharsets.UTF_8);

            // Formata o JSON para ser legível
            Object jsonObject = objectMapper.readValue(jsonDocs, Object.class); // Convert JSON string to an object
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject); // Format the JSON

            // Create the "docs" folder if it doesn't exist
            if (!Files.exists(Paths.get("docs"))) {
                Files.createDirectories(Paths.get("docs"));
            }

            // Save de JSON file
            FileWriter fileWriter = new FileWriter("docs/smb-api.json");
            fileWriter.write(prettyJson);
            fileWriter.close();

            System.out.println("Documentação OpenAPI salva em docs/openapi.json");
        } catch (IOException e) {
            System.err.println("Erro ao salvar documentação OpenAPI: " + e.getMessage());
        }
    }
}