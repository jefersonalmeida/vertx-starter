package com.jefersonalmeida.vertx_starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    final var vertx = Vertx.vertx();
    vertx.deployVerticle(new Main());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer().requestHandler(req ->
        req.response()
          .putHeader("content-type", "text/plain")
          .end("Hello from Vert.x!"))
      .listen(8888, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          LOG.debug("HTTP server started on port 8888");
//          System.out.println("HTTP server started on port 8888");
        } else {
          startPromise.fail(http.cause());
        }
      });
  }
}
