package org.example.xstream.app;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.xstream.api.FindAllQuery;
import org.example.xstream.api.FindByIdQuery;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class MyQueryHandler {

  @QueryHandler
  public QueryView handle(FindByIdQuery query) {
    log.info("MyQueryHandler: handling query {}", query);
    QueryView res = new QueryView(query.id(), "some name");
    log.info("MyQueryHandler: query single result: {}", res);
    return res;
  }

  @QueryHandler
  public List<QueryView> handle(FindAllQuery query) {
    log.info("MyQueryHandler: handling query {}", query);
    List<QueryView> list = new ArrayList<>();
    list.add(new QueryView(UUID.randomUUID().toString(), "1"));
    list.add(new QueryView(UUID.randomUUID().toString(), "2"));
    log.info("MyQueryHandler: query list result: {}", list);
    return list;
  }
}
