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
public class PiTestMixServiceTest extends PiTestMixServiceCommon {

    @Test
    public void shouldReturnOk200WithTwoStrings(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray().add("OneElement").add("twoElement")));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, "1:eee/1:nn/2:tt"));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

    @Test
    public void shouldReturnOk200WithTwoEmptyStrings(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray().add("").add("")));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, ""));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

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
                .put(PiTestMixService.KEY_RETURNED, "2:eeeeeee/1:iiiiiii/1:lllllll/3:oooooo/2:nn/1,3:tt"));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

    @Test
    public void shouldReturnOk200WithThreeStringsEquals(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()
                        .add("aaaabbbcc")
                        .add("aaaabbbcc")
                        .add("aaaabbbcc")
                ));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, "=:aaaa/=:bbb/=:cc"));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

    @Test
    public void shouldReturnOk200WithThreeStringsMix(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()
                        .add("yYyYyYyYyaaaabbbcc")
                        .add("aaaabbbccuuuuu")
                        .add("aaaabbbccuuuu")
                ));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, "2:uuuuu/1:yyyyy/=:aaaa/=:bbb/=:cc"));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

    @Test
    public void shouldReturnOk200WithThreeStringsMixTwoByTwo(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()
                        .add("rrrrrr aaa bbb zz")
                        .add("ttttt aaa cc czz")
                        .add("yyyy ccc bbb zz")
                ));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, "1:rrrrrr/2:ttttt/3:yyyy/1,2:aaa/1,3:bbb/2,3:ccc/=:zz"));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

    @Test
    public void shouldReturnOk200WithThreeStringsMixTwoByTwoChangeOrder1(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()
                        .add("yyyy ccc bbb zz")
                        .add("rrrrrr aaa bbb zz")
                        .add("ttttt aaa cc czz")

                ));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, "2:rrrrrr/3:ttttt/1:yyyy/2,3:aaa/1,2:bbb/1,3:ccc/=:zz"));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

    @Test
    public void shouldReturnOk200WithThreeStringsMixTwoByTwoChangeOrder2(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()
                        .add("yyyy ccc bbb zz")
                        .add("ttttt aaa cc czz")
                        .add("rrrrrr aaa bbb zz")

                ));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, "3:rrrrrr/2:ttttt/1:yyyy/2,3:aaa/1,3:bbb/1,2:ccc/=:zz"));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

    @Test
    public void shouldReturnOk200WithThreeStringsMixTwoByTwoChangeOrder3(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()
                        .add("ttttt aaa cc czz")
                        .add("yyyy ccc bbb zz")
                        .add("rrrrrr aaa bbb zz")

                ));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, "3:rrrrrr/1:ttttt/2:yyyy/1,3:aaa/2,3:bbb/1,2:ccc/=:zz"));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

    @Test
    public void shouldReturnOk200WithOneEmptyStrings(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()
                        .add("aaaa bbb cc f g")
                        .add("")
                ));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, "1:aaaa/1:bbb/1:cc"));



        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

    @Test
    public void shouldReturnOk200WithOneEmptyStringsOfThree(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()
                        .add("aaaa bbb cc e f")
                        .add("")
                        .add("g hh iii kkkkkk bbb")
                ));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, "3:kkkkkk/1:aaaa/1,3:bbb/3:iii/1:cc/3:hh"));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

    @Test
    public void shouldReturnOk200WithThreeStringsOneEquals(final TestContext context) {

        final String json = Json.encodePrettily(new JsonObject()
                .put(KEY_STRINGS_TO_PROCESS, new JsonArray()
                        .add("aaaa bbb")
                        .add("bbb")
                        .add("aaaa bbb")
                ));

        final String result = Json.encodePrettily(new JsonObject()
                .put(PiTestMixService.KEY_RETURNED, "1,3:aaaa/=:bbb"));

        this.checkResponse(context, json, result, HttpResponseStatus.OK.code(), true);

    }

}
