package com.jefersonalmeida.vertx_starter.json;

public class Person {
  private Integer id;
  private String name;
  private Boolean lovesVertx;

  public Person() {
    // Default constructor for Jackson
  }

  public Person(Integer id, String name, Boolean lovesVertx) {
    this.id = id;
    this.name = name;
    this.lovesVertx = lovesVertx;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean isLovesVertx() {
    return lovesVertx;
  }

  public void setLovesVertx(Boolean lovesVertx) {
    this.lovesVertx = lovesVertx;
  }
}
