package org.example.jackson;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
class MyDto {

  String name;
  int num;
  boolean b;
}

@Value
@Builder(toBuilder = true)
class MyDtoContainer {

  List<MyDto> list;
}
