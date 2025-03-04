package com.manager.smbuilding.infrastructure.configuration;

import com.manager.smbuilding.application.service.OpenApiDocumentationService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class OpenApiDocumentationSaver implements ApplicationRunner {

    private final OpenApiDocumentationService openApiDocumentationService;

    public OpenApiDocumentationSaver(OpenApiDocumentationService openApiDocumentationService) {
        this.openApiDocumentationService = openApiDocumentationService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        openApiDocumentationService.saveOpenApiDocumentation();
    }
}
