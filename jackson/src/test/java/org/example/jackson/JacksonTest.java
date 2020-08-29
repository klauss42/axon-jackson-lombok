package org.example.jackson;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.axonframework.serialization.SerializedObject;
import org.axonframework.serialization.json.JacksonSerializer;
import org.junit.jupiter.api.Test;

public class JacksonTest {


  public ObjectMapper mapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    return mapper;
  }

  @Test
  public void serialize_array_correct_result() throws Exception {
    ObjectMapper mapper = mapper();
    List<MyDto> listOfDtos = Lists.newArrayList(
        MyDto.builder().name("a").num(1).b(true).build(),
        MyDto.builder().name("b").num(2).b(true).build(),
        MyDto.builder().name("c").num(3).b(true).build());
    String jsonArray = mapper.writeValueAsString(listOfDtos);

    // [{"stringValue":"a","intValue":1,"booleanValue":true},
    // {"stringValue":"bc","intValue":3,"booleanValue":false}]

    MyDto[] asArray = mapper.readValue(jsonArray, MyDto[].class);
    assertThat(asArray[0], instanceOf(MyDto.class));
  }

  @Test
  public void serialize_list_wrong_result() {

    JacksonSerializer serializer = JacksonSerializer.builder()
        .objectMapper(mapper())
        .build();

    List<MyDto> objectToSerialize = new ArrayList<>();
    objectToSerialize.add(MyDto.builder().name("a").num(1).b(true).build());
    objectToSerialize.add(MyDto.builder().name("b").num(2).b(true).build());
    objectToSerialize.add(MyDto.builder().name("c").num(3).b(true).build());

    SerializedObject<String> serializedResult = serializer
        .serialize(objectToSerialize, String.class);

    List<MyDto> deserializedResult = serializer.deserialize(serializedResult);
    // we get a list of LinkedHashMap -> wrong
    assertThat((Object) deserializedResult.get(0), instanceOf(LinkedHashMap.class));
  }

  @Test
  public void serialize_list() throws Exception {
    JacksonSerializer serializer = JacksonSerializer.builder()
        .objectMapper(mapper())
        .defaultTyping()
        .build();

    List<MyDto> objectToSerialize = new ArrayList<>();
    objectToSerialize.add(MyDto.builder().name("a").num(1).b(true).build());
    objectToSerialize.add(MyDto.builder().name("b").num(2).b(true).build());
    objectToSerialize.add(MyDto.builder().name("c").num(3).b(true).build());

    SerializedObject<String> serializedResult = serializer
        .serialize(objectToSerialize, String.class);

    List<MyDto> deserializedResult = serializer.deserialize(serializedResult);

    assertEquals(objectToSerialize, deserializedResult);
  }
}

