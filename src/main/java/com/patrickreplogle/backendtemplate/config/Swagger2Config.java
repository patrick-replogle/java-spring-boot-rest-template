package com.patrickreplogle.backendtemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configures the default Swagger Documentation
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config
{
    /**
     * Configures what to document using Swagger
     *
     * @return A Docket which is the primary interface for Swagger configuration
     */
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.patrickreplogle.backendtemplate"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(apiEndPointsInfo());
    }

    /**
     * Configures some information related to the Application for Swagger
     *
     * @return ApiInfo a Swagger object containing identification information for this application
     */
    private ApiInfo apiEndPointsInfo()
    {
        return new ApiInfoBuilder().title("backend-template")
                .description("Spring Boot REST API template")
                .contact(new Contact("Patrick Replogle",
                        "https://github.com/patrick-replogle",
                        "patrickr1138@gmail.com"))
                .license("MIT")
                .licenseUrl("https://github.com/patrick-replogle")
                .version("1.0.0")
                .build();
    }
}
