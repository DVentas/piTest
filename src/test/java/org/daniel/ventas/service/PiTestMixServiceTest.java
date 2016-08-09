package org.daniel.ventas.service;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.daniel.ventas.common.Constants;
import org.daniel.ventas.service.common.PiTestMixServiceCommon;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class PiTestMixServiceTest extends PiTestMixServiceCommon {

    @Test
    public void shouldReturnOk200WithTwoStrings(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray().add("OneElement").add("twoElement")));

        this.checkResponse(context, json, "", Constants.HTTP_CODE_OK);

    }


}