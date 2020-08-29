package org.example.xstream.api;

import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
//@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateCommand {

  @TargetAggregateIdentifier
  String id;
}
