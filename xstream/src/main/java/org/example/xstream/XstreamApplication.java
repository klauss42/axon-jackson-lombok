package org.example.xstream;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class XstreamApplication {

  public static void main(String[] args) {
    SpringApplication.run(XstreamApplication.class, args);
  }

  @Configuration
  public static class SwaggerConfig {

    @Bean
    public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .paths(PathSelectors.any())
          .build();
    }
  }


  @Configuration
  public static class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
      ObjectMapper mapper = new ObjectMapper();
      // needed to properly serialize/deserialize Optional's
      //    mapper.registerModule(new Jdk8Module());
      //    mapper.registerModule(new ParameterNamesModule());
      //    mapper.registerModule(new JavaTimeModule());
      //    mapper.registerModule(new JavaTimeModule());
      mapper.findAndRegisterModules(); //Registers all modules on classpath

      // needed to be able to serialize/deserialize commands/events/queries that do not use Java-beans
      // getter's but instead fluent style accessors (lombok.accessors.fluent)
      mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
      mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      return mapper;
    }
  }

}
