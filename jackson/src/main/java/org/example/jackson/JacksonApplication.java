package org.example.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.axonframework.serialization.ChainingConverter;
import org.axonframework.serialization.RevisionResolver;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class JacksonApplication {

  public static void main(String[] args) {
    SpringApplication.run(JacksonApplication.class, args);
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
  public static class JacksonConfiguration implements BeanClassLoaderAware {

    private ClassLoader beanClassLoader;

    @Primary
    @Bean
    public Serializer serializer(RevisionResolver revisionResolver) {
      ChainingConverter converter = new ChainingConverter(beanClassLoader);
      return JacksonSerializer.builder()
          .revisionResolver(revisionResolver)
          .converter(converter)
          // create a new mapper instance for Axon, as the JacksonSerializer changes the instance
          .objectMapper(createMapperInstance())
          .defaultTyping()
          .build();
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
    }

    @Bean
    public ObjectMapper objectMapper() {
      // this mapper instance will be used by Spring
      return createMapperInstance();
    }

    private ObjectMapper createMapperInstance() {
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
      //    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//      // to fix deserialization of List<Bean>,
//      // see https://github.com/AxonFramework/AxonFramework/issues/1331
//      mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);

      return mapper;
    }
  }

}
