package org.example.jackson.app;

import java.util.List;
import lombok.Value;

@Value(staticConstructor = "of")
public class QueryResult<T> {

  List<T> list;
}
