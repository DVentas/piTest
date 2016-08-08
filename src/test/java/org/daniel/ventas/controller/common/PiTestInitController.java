package org.daniel.ventas.controller.common;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import org.daniel.ventas.controller.PiTestController;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.net.ServerSocket;


public class PiTestInitController {

    protected String keyForJson = "randomKey";
    protected Vertx vertx;
    protected Integer port;
    protected String host = "localhost";

    /*
     * Before executing our test, let's deploy our verticle.
     * <p/>
     * This method instantiates a new Vertx and deploy the verticle. Then, it waits in the verticle has successfully
     * completed its start sequence (thanks to `context.asyncAssertSuccess`).
     *
     * @param context the test context.
     */
    @Before
    public void setUp(TestContext context) throws IOException {
        vertx = Vertx.vertx();

        // We pass the options as the second parameter of the deployVerticle method.
        vertx.deployVerticle(PiTestController.class.getName(), getOptions(), context.asyncAssertSuccess());
    }

    /*
     * This method, called after our test, just cleanup everything by closing the vert.x instance
     *
     * @param context the test context
     */
    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    protected DeploymentOptions getOptions() throws IOException {
        // Let's configure the verticle to listen on the 'test' port (randomly picked).
        // We create deployment options and set the _configuration_ json object:
        ServerSocket socket = new ServerSocket(0);
        port = socket.getLocalPort();
        socket.close();

        final JsonObject server = new JsonObject();
        server.put("host", host);
        server.put("port", port);

        return new DeploymentOptions()
                .setConfig(new JsonObject()
                        .put("server", server)
                );
    }


}
