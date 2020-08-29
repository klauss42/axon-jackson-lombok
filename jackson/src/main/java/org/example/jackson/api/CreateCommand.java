package org.example.jackson.api;

import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
@Builder(toBuilder = true)
public class CreateCommand {

  @TargetAggregateIdentifier
  String id;
}
