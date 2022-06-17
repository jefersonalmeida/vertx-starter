package com.jefersonalmeida.vertx.starter.eventbus.customcodec;

public class Ping {
  private String message;
  private Boolean enabled;

  public Ping() {
  }

  public Ping(String message, Boolean enabled) {
    this.message = message;
    this.enabled = enabled;
  }

  public String getMessage() {
    return message;
  }

  public Boolean isEnabled() {
    return enabled;
  }

  @Override
  public String toString() {
    return "Ping{" +
      "message='" + message + '\'' +
      ", enabled=" + enabled +
      '}';
  }
}
