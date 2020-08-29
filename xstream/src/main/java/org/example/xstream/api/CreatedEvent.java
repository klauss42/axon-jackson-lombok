package org.example.xstream.api;

import lombok.Builder;
import lombok.Value;

@Value
//@AllArgsConstructor
@Builder(toBuilder = true)
public class CreatedEvent {

  String id;
}
