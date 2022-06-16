package com.jefersonalmeida.vertx_starter.json;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectExampleTest {

  @Test
  void jsonObjectCanBeMapped() {
    final var jsonObject = new JsonObject();
    jsonObject.put("id", 1);
    jsonObject.put("name", "Helen");
    jsonObject.put("loves_vertx", true);

    final var encoded = jsonObject.encode();

    assertEquals("{\"id\":1,\"name\":\"Helen\",\"loves_vertx\":true}", encoded);

    final var decodedJsonObject = new JsonObject(encoded);
    assertEquals(jsonObject, decodedJsonObject);
  }

  @Test
  void jsonObjectCanBeCreatedFromMap() {
    final var map = new HashMap<String, Object>();
    map.put("id", 1);
    map.put("name", "Helen");
    map.put("loves_vertx", true);

    final var asJsonObject = new JsonObject(map);
    assertEquals(map, asJsonObject.getMap());
    assertEquals(1, asJsonObject.getInteger("id"));
    assertEquals("Helen", asJsonObject.getString("name"));
    assertEquals(true, asJsonObject.getBoolean("loves_vertx"));
  }
}
