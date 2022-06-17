package com.jefersonalmeida.vertx.starter.json;

import io.vertx.core.json.JsonArray;
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

  @Test
  void jsonArrayCanBeMapped() {
    final var jsonArray = new JsonArray();
    jsonArray
      .add(new JsonObject().put("id", 1))
      .add(new JsonObject().put("id", 2))
      .add(new JsonObject().put("id", 3))
      .add("randomValue")
    ;

    assertEquals("[{\"id\":1},{\"id\":2},{\"id\":3},\"randomValue\"]", jsonArray.encode());
  }

  @Test
  void canMapJavaObjects() {
    final var personOne = new Person(1, "Helen", true);

    final var helen = JsonObject.mapFrom(personOne);
    assertEquals(personOne.getId(), helen.getInteger("id"));
    assertEquals(personOne.getName(), helen.getString("name"));
    assertEquals(personOne.isLovesVertx(), helen.getBoolean("lovesVertx"));

    final var personTow = helen.mapTo(Person.class);
    assertEquals(personOne.getId(), personTow.getId());
    assertEquals(personOne.getName(), personTow.getName());
    assertEquals(personOne.isLovesVertx(), personTow.isLovesVertx());
  }
}
