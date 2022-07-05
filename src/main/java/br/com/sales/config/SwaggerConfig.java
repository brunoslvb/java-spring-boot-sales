package br.com.sales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.sales.api.controllers"))
            .paths(PathSelectors.any())
            .build()
            .securityContexts(Arrays.asList(securityContext()))
            .securitySchemes(Arrays.asList(apiKey()))
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
            .title("Sales API")
            .description("Api do projeto de vendas")
            .version("1.0")
            .contact(contact())
            .build();
    }

    private Contact contact(){
        return new Contact(
            "Bruno da Silva Barros",
            "https://github.com/brunoslvb/java-spring-boot-sales",
            "brunosilva2365@gmail.com"
        );
    }

    public ApiKey apiKey(){
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scores = new AuthorizationScope[1];
        scores[0] = authorizationScope;
        SecurityReference securityReference = new SecurityReference("JWT", scores);
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(securityReference);
        return auths;
    }



}
