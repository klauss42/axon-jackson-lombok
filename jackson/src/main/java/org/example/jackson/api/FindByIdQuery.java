package org.example.jackson.api;

import lombok.Value;

@Value(staticConstructor = "of")
//@Value
//@Builder(toBuilder = true)
public class FindByIdQuery {

  String id;
}
