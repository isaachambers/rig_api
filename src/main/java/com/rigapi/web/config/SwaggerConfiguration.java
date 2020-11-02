package com.rigapi.web.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@EnableConfigurationProperties(SwaggerConfiguration.ApiProperties.class)
public class SwaggerConfiguration {

  @Bean
  public static ApiProperties apiProperties() {
    return new ApiProperties();
  }

  @Bean
  public static Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.rigapi"))
        .build()
        .pathMapping("/")
        .forCodeGeneration(true)
        .genericModelSubstitutes(ResponseEntity.class)
        .directModelSubstitute(MultiValueMap.class, Map.class)
        .apiInfo(buildApiInfo());
  }

  private static ApiInfo buildApiInfo() {
    var apiProperties = apiProperties();

    return new ApiInfoBuilder()
        .title(apiProperties.getTitle())
        .description(apiProperties.getDescription())
        .version(apiProperties.getVersion())
        .build();
  }

  @ConfigurationProperties(prefix = "api.info")
  public static class ApiProperties {
    private String title;
    private String description;
    private String version;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getVersion() {
      return version;
    }

    public void setVersion(String version) {
      this.version = version;
    }
  }
}
