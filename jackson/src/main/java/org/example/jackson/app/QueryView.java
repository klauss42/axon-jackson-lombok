package org.example.jackson.app;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class QueryView {

  String id;
  String name;
}
