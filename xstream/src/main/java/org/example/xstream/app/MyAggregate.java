package org.example.xstream.app;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.xstream.api.CreateCommand;
import org.example.xstream.api.CreatedEvent;

@Slf4j
@Aggregate
class MyAggregate {

  @AggregateIdentifier
  private String id;

  public MyAggregate() {
    // Required by Axon
  }

  @CommandHandler
  public MyAggregate(CreateCommand command) {
    log.info("MyAggregate: create app {}", command);
    AggregateLifecycle.apply(CreatedEvent.builder().id(command.id()).build());
  }

  @EventSourcingHandler
  public void on(CreatedEvent event) {
    log.info("MyAggregate: created event {}", event);
    id = event.id();
  }

}
