package org.daniel.ventas.service.common;

import io.vertx.core.DeploymentOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.daniel.ventas.controller.common.PiTestInitController;

import java.io.IOException;

public class PiTestDiffServiceCommon extends PiTestInitController {

    protected final static String KEY_STRINGS_TO_PROCESS = "randomKey";
    private final static String KEY_STRINGS_CONFIG = "key.stringArray";

    @Override
    protected DeploymentOptions getOptions() throws IOException {
        final DeploymentOptions options = super.getOptions();

        options.getConfig().put(KEY_STRINGS_CONFIG, KEY_STRINGS_TO_PROCESS);

        return options;
    }

    protected void checkResponse (final TestContext context, final String jsonBody, final String stringInBody, final Integer statusCode ) {
        // This test is asynchronous, so get an async handler to inform the test when we are done.
        final Async async = context.async();

        // We create a HTTP client and query our application. When we get the response we check it contains stringInBody
        //  message and status http code is statusCode. Then, we call the `complete` method on the async
        // handler to declare this async (and here the test) done. Notice that the assertions are made on the 'context'
        // object and are not Junit assert. This ways it manage the async aspect of the test the right way.
        vertx.createHttpClient().post(port, host, "/diff")
                .putHeader("content-type", "application/json")
                .putHeader("content-length", Integer.toString(jsonBody.length()))
                .handler(response -> {
                    context.assertEquals(response.statusCode(), statusCode);
                    response.bodyHandler(body -> {
                        context.assertTrue(body.toString().contains(stringInBody));
                        async.complete();
                    });
                })
                .write(jsonBody)
                .end();
    }

}
