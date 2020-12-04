package com.agh.maingateway.Swagger;

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
        resources.add(swaggerResource("accounts-service", "/accounts-service/v2/api-docs"));
        resources.add(swaggerResource("baskets-service", "/baskets-service/v2/api-docs"));
        resources.add(swaggerResource("products-service", "/products-service/v2/api-docs"));
        resources.add(swaggerResource("orders-service", "/orders-service/v2/api-docs"));
        resources.add(swaggerResource("storage-service", "/storage-service/v2/api-docs"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
