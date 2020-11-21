package com.agh.bookstoreGatewaymaster.Swagger;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("accounts-service"));
        resources.add(swaggerResource("baskets-service"));
        resources.add(swaggerResource("products-service"));
        resources.add(swaggerResource("orders-service"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/v2/api-docs"); //TODO: ustawić endpointy
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
