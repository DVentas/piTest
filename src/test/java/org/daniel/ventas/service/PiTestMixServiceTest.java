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

        final String result = Json.encodePrettily(new JsonObject()
                .put(Constants.KEY_RETURNED, "1:eee/2:tt/1:nn"));

        this.checkResponse(context, json, result, Constants.HTTP_CODE_OK, true);

    }

    @Test
    public void shouldReturnOk200WithTwoEmptyStrings(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray().add("").add("")));

        final String result = Json.encodePrettily(new JsonObject()
                .put(Constants.KEY_RETURNED, ""));

        this.checkResponse(context, json, result, Constants.HTTP_CODE_OK, true);

    }

    @Test
    public void shouldReturnOk200WithThreeStrings(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()
                                                    .add("firstelementiiiiiillllll")
                                                    .add("2 12213(( ^ seeeecondAYI32  ++ - element")
                                                    .add("third&elementoooooo")
                                                ));

        final String result = Json.encodePrettily(new JsonObject()
                .put(Constants.KEY_RETURNED, "2:eeeeeee/1:iiiiiii/1:lllllll/3:oooooo/1,3:tt/2:nn"));

        this.checkResponse(context, json, result, Constants.HTTP_CODE_OK, true);

    }


}
