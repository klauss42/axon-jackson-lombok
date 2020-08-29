package org.example.jackson.api;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CreatedEvent {

  String id;
}
