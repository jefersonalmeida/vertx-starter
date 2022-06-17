package com.jefersonalmeida.vertx.vertx_starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PublishSubscribeExample {

  public static void main(String[] args) {
    final var vertx = Vertx.vertx();
    vertx.deployVerticle(new Publish());
    vertx.deployVerticle(new SubscriberOne());
    vertx.deployVerticle(SubscriberTwo.class.getName(), new DeploymentOptions().setInstances(2));
  }

  public static class Publish extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(Duration.ofSeconds(10).toMillis(), id ->
        vertx.eventBus().publish(Publish.class.getName(), "A message for everyone!")
      );
    }
  }

  public static class SubscriberOne extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriberOne.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Publish.class.getName(), message ->
        LOG.debug("Received: {}", message.body())
      );
    }
  }

  public static class SubscriberTwo extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriberTwo.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Publish.class.getName(), message ->
        LOG.debug("Received: {}", message.body())
      );
    }
  }
}
