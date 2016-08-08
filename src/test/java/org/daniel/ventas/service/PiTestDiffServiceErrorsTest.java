package org.daniel.ventas.service;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.daniel.ventas.service.common.PiTestDiffServiceCommon;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class PiTestDiffServiceErrorsTest extends PiTestDiffServiceCommon {

    @Test
    public void shouldReturnError400WhenMalformedJSON(final TestContext context) {

        this.checkResponse(context, "", "Malformed JSON", 400);

    }

    @Test
    public void shouldReturnError400WhenNotBodySend(final TestContext context) {

        this.checkResponse(context, Json.encodePrettily(new JsonObject()), "Should send json object named ", 400);

    }

    @Test
    public void shouldReturnError400WhenMalformedBodySend(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, ""));

        this.checkResponse(context, json, "Expect array of Strings in key", 400);

    }

    @Test
    public void shouldReturnError400WhenArrayWithoutElements(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()));

        this.checkResponse(context, json, "object should contain atleast two strings", 400);

    }

    @Test
    public void shouldReturnError400WhenArrayWithOneElement(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray().add("OneElement")));

        this.checkResponse(context, json, "object should contain atleast two strings", 400);

    }

    @Test
    public void shouldReturnOk200WithTwoStrings(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray().add("OneElement").add("twoElement")));

        this.checkResponse(context, json, "", 200);

    }

}
