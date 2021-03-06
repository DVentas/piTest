package org.daniel.ventas.controller;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.daniel.ventas.service.PiTestMixService;

public class PiTestController extends AbstractVerticle {

    @Override
    public void start (Future<Void> fut) {

        startWebApp(
                (http) -> completeStartup(http, fut)
        );
    }

    private void startWebApp(Handler<AsyncResult<HttpServer>> next) {
        // Create a router object.
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        router.post("/api/mix")
            .handler(
                new PiTestMixService(
                    config().getString("key.stringArray", "stringsToProcess")
                )::mix
        );

        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx
            .createHttpServer()
            .requestHandler(router::accept)
            .listen(
                // Retrieve the port from the configuration,
                // default to 8080.
                config().getJsonObject("server").getInteger("port", 8080),

                // Retrieve the host from the configuration,
                // default to localhost.
                config().getJsonObject("server").getString("host", "localhost"),

                next::handle
            );
    }

    private void completeStartup(AsyncResult<HttpServer> http, Future<Void> fut) {
        if (http.succeeded()) {
            fut.complete();
        } else {
            fut.fail(http.cause());
        }
    }

}
