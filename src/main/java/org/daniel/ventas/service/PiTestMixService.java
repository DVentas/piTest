package org.daniel.ventas.service;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.codehaus.plexus.util.StringUtils;
import org.daniel.ventas.model.MixResult;

import java.util.*;

public class PiTestMixService {

    private final String jsonKeyStrings;

    public PiTestMixService(final String jsonKeyStrings) {
        this.jsonKeyStrings = jsonKeyStrings;
    }

    /**
     *
     * @param routingContext
     * @throws RuntimeException
     */
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

    /**
     *
     * @param stringsToProcess
     * @return mix all strings in list
     */
    private JsonObject processMix(final List<String> stringsToProcess) {

        String result;

        try {

            final Set<MixResult> orderedCharactersCount = this.getOrderedCharactersCount(stringsToProcess);

            result = this.createResultByCharacter(orderedCharactersCount, stringsToProcess.size());

        } catch (ClassCastException cce) {
            throw new RuntimeException(
                    new StringBuffer()
                            .append("All object in array '")
                            .append(this.jsonKeyStrings)
                            .append("' must be a String object.")
                            .toString()
            );
        }


        return new JsonObject().put("result", result);
    }

    /**
     *
     * @param stringsToProcess
     * @return return ordered array with max count for every character
     */
    private Set<MixResult> getOrderedCharactersCount(final List<String> stringsToProcess) {

        final Map<Character, MixResult> mix = new TreeMap<>();

        int indexOfString = 0;

        Map<Character, Integer> countChars;

        // Count chars for string
        for (String  strToProcess :  stringsToProcess) {

            ++indexOfString;

            countChars = this.countCharsForString(strToProcess.replaceAll("[^a-z]", ""));

            for(Map.Entry<Character, Integer> charCount : countChars.entrySet()) {

                if (charCount.getValue() > 1) {
                    this.checkIfShouldAddEntry(mix, charCount.getKey(), charCount.getValue(), indexOfString);
                }

            }

        }

        // Order results
        final Set<MixResult> mixOrdered = new TreeSet<>(Collections.reverseOrder());
        mixOrdered.addAll(mix.values());

        return mixOrdered;
    }

    /**
     *
     * @param strToProcess
     * @return One entry for ecery character with count in string
     */
    private Map<Character, Integer> countCharsForString (final String strToProcess) {
        final Map<Character, Integer> countChars = new HashMap<>();

        for (Character character : strToProcess.toCharArray()) {

            int actualCount = countChars.get(character) == null ? 0 : countChars.get(character);

            countChars.put(character, actualCount + 1);

        }

        return countChars;
    }

    /**
     *
     * @param mix
     * @param character
     * @param count
     * @param indexOfString
     */
    private void checkIfShouldAddEntry (
            final Map<Character, MixResult> mix,
            final Character character,
            final Integer count,
            final Integer indexOfString) {

        MixResult mr;

        if (mix.containsKey(character)) {

            mr = mix.get(character);

            switch (mr.getCount().compareTo(count)) {

                case 1:
                    // Character has better count for other index, nothing to do here
                    break;
                case 0:
                    // Character has same count, add index to list
                    mr.getIndexes().add(indexOfString);
                    break;
                case -1:
                    // Actual character has more counts that old, replace mixResult
                    mix.put(character,
                            new MixResult(character, count, new ArrayList<Integer>() {{ add(indexOfString); }}));
                    break;
            }
        } else {
            // havent entry in hash, put then new
            mix.put(character, new MixResult(character, count, new ArrayList<Integer>() {{ add(indexOfString); }}));

        }

    }

    /**
     *
     * @param finalCountCharacters
     * @param totalStrings
     * @return string that contains complete result
     */
    private String createResultByCharacter(final Set<MixResult> finalCountCharacters, final int totalStrings) {

        final Iterator<MixResult> mixResults = finalCountCharacters.iterator();
        final StringBuilder result = new StringBuilder();

        while (mixResults.hasNext()) {
            result.append(buildString(mixResults.next(), totalStrings));

            result.append("/");
        }

        // Remove last / of the resut
        if (result.lastIndexOf("/") > -1) {
            result.deleteCharAt(result.lastIndexOf("/"));
        }

        return result.toString();

    }

    /**
     *
     * @param result
     * @param numberOfStrings
     * @return string that describes result
     */
    private String buildString (final MixResult result, final Integer numberOfStrings) {
        final StringBuilder resultByCharAndCount = new StringBuilder();

        if (result.getIndexes().size() == numberOfStrings) {
            resultByCharAndCount.append("=");
        } else {
            resultByCharAndCount.append(StringUtils.join(result.getIndexes().iterator(), ","));
        }

        resultByCharAndCount.append(":");

        resultByCharAndCount.append(
                new String(new char[result.getCount()]).replace("\0", String.valueOf(result.getCharacter())));

        return resultByCharAndCount.toString();
    }
}
