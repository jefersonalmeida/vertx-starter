package com.jefersonalmeida.vertx.vertx_starter;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExampleTest {

  private static final Logger LOG = LoggerFactory.getLogger(FuturePromiseExampleTest.class);

  @Test
  void promise_success(Vertx vertx, VertxTestContext context) {
    final var promise = Promise.<String>promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Success");
      LOG.debug("Success");
      context.completeNow();
    });
    LOG.debug("End");
  }

  @Test
  void promise_failure(Vertx vertx, VertxTestContext context) {
    final var promise = Promise.<String>promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.fail(new RuntimeException("Failed!"));
      LOG.debug("Failed");
      context.completeNow();
    });
    LOG.debug("End");
  }

  @Test
  void future_success(Vertx vertx, VertxTestContext context) {
    final var promise = Promise.<String>promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Success");
      LOG.debug("Timer done.");
    });

    final var future = promise.future();
    future
      .onSuccess(result -> {
        LOG.debug("Result: {}", result);
        context.completeNow();
      })
      .onFailure(context::failNow);
  }

  @Test
  void future_failure(Vertx vertx, VertxTestContext context) {
    final var promise = Promise.<String>promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.fail(new RuntimeException("Failed!"));
      LOG.debug("Timer done.");
    });

    final var future = promise.future();
    future
      .onSuccess(context::failNow)
      .onFailure(error -> {
        LOG.debug("Result: ", error);
        context.completeNow();
      });
  }

  @Test
  void future_map(Vertx vertx, VertxTestContext context) {
    final var promise = Promise.<String>promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Success");
      LOG.debug("Timer done.");
    });

    final var future = promise.future();
    future
      .map(asString -> {
        LOG.debug("Map String to JsonObject");
        final var jsonObject = new JsonObject().put("key", asString);
        LOG.debug("Map result: {} of type {}", jsonObject, jsonObject.getClass().getSimpleName());
        return jsonObject;
      })
      .map(jsonObject -> new JsonArray().add(jsonObject))
      .onSuccess(result -> {
        LOG.debug("Result: {} of type {}", result, result.getClass().getSimpleName());
        context.completeNow();
      })
      .onFailure(context::failNow);
  }

  @Test
  void future_coordination(Vertx vertx, VertxTestContext context) {
    vertx.createHttpServer()
      .requestHandler(request -> LOG.debug("{}", request))
      .listen(10_000)
      .compose(server -> {
        LOG.debug("Another task");
        return Future.succeededFuture(server);
      })
      .compose(server -> {
        LOG.debug("Even more");
        return Future.succeededFuture(server);
      })
      .onFailure(context::failNow)
      .onSuccess(server -> {
        LOG.debug("Server started on port {}", server.actualPort());
        context.completeNow();
      });
  }

  @Test
  void future_composition(Vertx vertx, VertxTestContext context) {
    var one = Promise.<Void>promise();
    var two = Promise.<Void>promise();
    var three = Promise.<Void>promise();

    var featureOne = one.future();
    var featureTwo = two.future();
    var featureThree = three.future();

    CompositeFuture.all(featureOne, featureTwo, featureThree)
      .onFailure(context::failNow)
      .onSuccess(result -> {
        LOG.debug("Success");
        context.completeNow();
      });

    // Complete futures
    vertx.setTimer(500, id -> {
      one.complete();
      two.complete();
//      three.fail("Three failed"); pass to use CompositeFuture.any
      three.complete();
    });
  }
}
