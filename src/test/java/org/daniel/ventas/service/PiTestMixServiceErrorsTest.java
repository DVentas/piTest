package org.daniel.ventas.service;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.daniel.ventas.service.common.PiTestMixServiceCommon;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class PiTestMixServiceErrorsTest extends PiTestMixServiceCommon {

    @Test
    public void shouldReturnError400WhenMalformedJSON(final TestContext context) {

        this.checkResponse(context, "", "Malformed JSON", HttpResponseStatus.BAD_REQUEST.code());

    }

    @Test
    public void shouldReturnError400WhenNotBodySend(final TestContext context) {

        this.checkResponse(context, Json.encodePrettily(new JsonObject()), "Should send json object named ",
                HttpResponseStatus.BAD_REQUEST.code());

    }

    @Test
    public void shouldReturnError400WhenMalformedBodySend(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, ""));

        this.checkResponse(context, json, "Expect array of Strings in key", HttpResponseStatus.BAD_REQUEST.code());

    }

    @Test
    public void shouldReturnError400WhenArrayWithoutElements(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()));

        this.checkResponse(context, json, "object should contain atleast two strings", HttpResponseStatus.BAD_REQUEST.code());

    }

    @Test
    public void shouldReturnError400WhenArrayWithOneElement(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray().add("OneElement")));

        this.checkResponse(context, json, "object should contain atleast two strings", HttpResponseStatus.BAD_REQUEST.code());

    }

    @Test
    public void shouldReturnError400WhenElementsInArrayNotStrings(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray().add("OneElement").add(2)));

        this.checkResponse(context, json, "All object in array ", HttpResponseStatus.BAD_REQUEST.code());

    }

    @Test
    public void shouldReturnOk200WithTwoStrings(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray().add("OneElement").add("twoElement")));

        this.checkResponse(context, json, "", HttpResponseStatus.OK.code());

    }

}
