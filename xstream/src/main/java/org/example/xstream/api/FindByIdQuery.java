package org.example.xstream.api;

import lombok.Value;

//@Value
//@Builder(toBuilder = true)
@Value(staticConstructor = "of")
public class FindByIdQuery {

  String id;
}
