package org.example.jackson.app;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.jackson.api.CreateCommand;
import org.example.jackson.api.FindAllQuery;
import org.example.jackson.api.FindByIdQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/test")
@RestController
class MyController {

  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public MyController(CommandGateway commandGateway, QueryGateway queryGateway) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
  }

  @PostMapping("")
  public CompletableFuture<String> create() {
    return commandGateway.send(CreateCommand.builder().id(UUID.randomUUID().toString()).build());
  }


  @GetMapping("/{id}")
  public QueryView findSingle(@PathVariable("id") String id) {
    QueryView result = queryGateway.query(
        FindByIdQuery.of(id),
//        FindByIdQuery.builder().id(id).build(),
        ResponseTypes.instanceOf(QueryView.class)
    ).join();
    log.info("MyController: query single result: {}", result);
    return result;
  }

  @GetMapping("")
  public List<QueryView> findAll() {
    List<QueryView> result = queryGateway.query(
        FindAllQuery.of(),
        ResponseTypes.multipleInstancesOf(QueryView.class)
    ).join();
    log.info("MyController: query list result: {}", result);
    return result;
  }
}
