package org.daniel.ventas.controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.daniel.ventas.controller.common.PiTestInitController;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class PiTestControllerTest extends PiTestInitController {

    /*
     * Let's ensure that our application behaves our mix api.
     *
     * @param context the test context
     */
    @Test
    public void shouldRecieveAnswerFromMixAPI(TestContext context) {
        // This test is asynchronous, so get an async handler to inform the test when we are done.
        final Async async = context.async();

        // We create a HTTP client and query our application. When we get the response we check it not contains the
        // 'Resource not found' message and status http code is 400. Then, we call the `complete` method on the async
        // handler to declare this async (and here the test) done. Notice that the assertions are made on the 'context'
        // object and are not Junit assert. This ways it manage the async aspect of the test the right way.
        vertx.createHttpClient().post(port, host, "/api/mix")
            .handler(response -> {
                context.assertEquals(response.statusCode(), HttpResponseStatus.BAD_REQUEST.code());
                response.bodyHandler(body -> {
                    context.assertFalse(body.toString().contains("Resource not found"));
                    async.complete();
                });
            })
            .end();
    }

    /*
     * Let's ensure that our application behaves correctly.
     *
     * @param context the test context
     */
    @Test
    public void testUnknownURI(TestContext context) {
        // This test is asynchronous, so get an async handler to inform the test when we are done.
        final Async async = context.async();

        // We create a HTTP client and query our application. When we get the response we check it contains the
        // 'Resource not found' message. Then, we call the `complete` method on the async handler to declare this async
        // (and here the test) done. Notice that the assertions are made on the 'context' object and are not Junit
        // assert. This ways it manage the async aspect of the test the right way.
        vertx.createHttpClient().getNow(port, host, "/otherAPINotImplemented", response -> {
            context.assertEquals(response.statusCode(), HttpResponseStatus.NOT_FOUND.code());
            response.handler(body -> {
                context.assertTrue(body.toString().contains("Resource not found"));
                async.complete();
            });
        });
    }

}
