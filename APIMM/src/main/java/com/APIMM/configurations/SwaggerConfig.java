package com.APIMM.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.APIMM.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
        //PathSelectors.any();
    }
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Internal DP Rest API",
                "Spring Boot for Internal DP Rest API",
                "1.0",
                "Terms of service",
                new Contact("Minh Vo", "www.edulog.com", "minh.vo@karrostech.com"),
               "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }
}

