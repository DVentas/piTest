package org.daniel.ventas.service;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PiTestMixService {

    private final String jsonKeyStrings;

    public PiTestMixService(final String jsonKeyStrings) {
        this.jsonKeyStrings = jsonKeyStrings;
    }

    @SuppressWarnings("unchecked")
    public void mix(RoutingContext routingContext) throws RuntimeException {

        try {
            final JsonArray jsonStrings = routingContext
                    .getBodyAsJson()
                    .getJsonArray(this.jsonKeyStrings);


            if (jsonStrings == null) {
                throw new RuntimeException(
                    new StringBuilder()
                        .append("Should send json object named '")
                        .append(this.jsonKeyStrings)
                        .append("', with array of strings in body")
                        .toString()
                );

            }

            final List<String> stringsToProcess =
                    (List<String>)
                            jsonStrings.getList();

            if (stringsToProcess.size() <= 1) {
                throw new RuntimeException(
                    new StringBuilder()
                        .append("'")
                        .append(this.jsonKeyStrings)
                        .append("' object should contain atleast two strings")
                        .toString()
                );

            } else {
                routingContext.response()
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(processMix(stringsToProcess)));
            }

        } catch (ClassCastException cce) {
            final String error = new StringBuilder()
                    .append("Expect array of Strings in key")
                    .append("'")
                    .append(this.jsonKeyStrings)
                    .append("'")
                    .toString();

            routingContext.response().setStatusCode(400).end(error);

        } catch (DecodeException re) {
            routingContext.response().setStatusCode(400).end("Malformed JSON");

        } catch (RuntimeException re) {
            routingContext.response().setStatusCode(400).end(re.getMessage());

        }

    }

    private JsonObject processMix(final List<String> stringsToProcess) {

        int indexOfString = 0;

        final Map<Integer, Map<Character, Integer>> countCharactersOfSrings =
                new HashMap<>();

        try {
            for (String  strToProcess :  stringsToProcess) {

                Map<Character, Integer> countChars = new HashMap<>();

                for (Character character : strToProcess.toCharArray()) {

                    int actualCount = countChars.get(character) == null ? 0 : countChars.get(character);

                    countChars.put(character, actualCount + 1);

                }

                countCharactersOfSrings.put(indexOfString++, countChars);

            }
        } catch (ClassCastException cce) {
            throw new RuntimeException(
                new StringBuffer()
                    .append("All object in array '")
                    .append(this.jsonKeyStrings)
                    .append("' must be a String object.")
                    .toString()
            );
        }


        return new JsonObject().put("result", "22");
    }
}
