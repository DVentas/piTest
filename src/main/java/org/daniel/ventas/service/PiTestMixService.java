package org.daniel.ventas.service;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.codehaus.plexus.util.StringUtils;
import org.daniel.ventas.model.CountCharacter;
import org.daniel.ventas.model.CountString;

import java.util.*;

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

        StringBuilder result;

        final Map<Integer, Map<Character, Integer>> countCharactersOfSrings =
                new HashMap<>();

        try {

            String strToProcessCleaner ;

            Map<Character, Integer> countChars;

            // Count chars for string
            for (String  strToProcess :  stringsToProcess) {

                strToProcessCleaner = strToProcess.replaceAll("[^a-z]", "");

                countChars = new HashMap<>();

                for (Character character : strToProcessCleaner.toCharArray()) {

                    int actualCount = countChars.get(character) == null ? 0 : countChars.get(character);

                    countChars.put(character, actualCount + 1);

                }

                countCharactersOfSrings.put(++indexOfString, countChars);

            }

            final TreeSet<CountString> charsOrderingWithIndex = new TreeSet<>();

            CountString cs;
            List<Integer> arrayOfIndex;
            Map<Character, List<Integer>> mapOfCharacters;

            // Order chars for all strings
            for (Map.Entry<Integer, Map<Character, Integer>> stringMixing : countCharactersOfSrings.entrySet()) {

                for (Map.Entry<Character, Integer> countCharsInMixString : stringMixing.getValue().entrySet()) {

                    if (countCharsInMixString.getValue() > 1) {

                        if (charsOrderingWithIndex.contains(new CountString(countCharsInMixString.getValue()))) {
                            cs = charsOrderingWithIndex.ceiling(new CountString(countCharsInMixString.getValue()));


                            if (cs.getCharsWithSameCountWithIndexStrings().containsKey(countCharsInMixString.getKey())) {
                                cs.getCharsWithSameCountWithIndexStrings().get(countCharsInMixString.getKey())
                                        .add(stringMixing.getKey());
                            } else {

                                arrayOfIndex = new ArrayList<>();
                                arrayOfIndex.add(stringMixing.getKey());

                                cs.getCharsWithSameCountWithIndexStrings().put(countCharsInMixString.getKey(), arrayOfIndex);
                            }
                        } else {

                            arrayOfIndex = new ArrayList<>();
                            arrayOfIndex.add(stringMixing.getKey());

                            mapOfCharacters = new HashMap<>();
                            mapOfCharacters.put(countCharsInMixString.getKey(), arrayOfIndex);

                            cs = new CountString(countCharsInMixString.getValue(), mapOfCharacters);

                            charsOrderingWithIndex.add(cs);
                        }

                    }
                }
            }

            final Iterator<CountString> sumCountChars = charsOrderingWithIndex.iterator();

            final Map<Character, CountCharacter> finalCountCharacters = new HashMap<>();

            CountCharacter cc;

            // Unique chars for all strings
            while (sumCountChars.hasNext()) {

                cs = sumCountChars.next();

                for (Map.Entry<Character, List<Integer>> charListIndexes : cs.getCharsWithSameCountWithIndexStrings().entrySet()) {
                    cc = new CountCharacter(cs.getCount(), charListIndexes.getValue());

                    finalCountCharacters.put(charListIndexes.getKey(), cc);
                }

            }

            final Map<Integer, Map<Character, List<Integer>>> orderedCharsByCount = new TreeMap<>(Collections.reverseOrder());
            Map<Character, List<Integer>> finalCharsCount;

            //TODO
            // create result for any character
            for (Map.Entry<Character, CountCharacter> uniqueCharsCount : finalCountCharacters.entrySet()) {

                if (orderedCharsByCount.containsKey(uniqueCharsCount.getValue().getCount())) {

                    orderedCharsByCount.get(uniqueCharsCount.getValue().getCount())
                            .put(uniqueCharsCount.getKey(), uniqueCharsCount.getValue().getIndexes());

                } else {

                    finalCharsCount = new HashMap<>();
                    finalCharsCount.put(uniqueCharsCount.getKey(), uniqueCharsCount.getValue().getIndexes());

                    orderedCharsByCount.put(uniqueCharsCount.getValue().getCount(), finalCharsCount);
                }
            }


            // TODO
            // union all results

            result = new StringBuilder();

            for (Map.Entry<Integer, Map<Character, List<Integer>>> resultOrdered : orderedCharsByCount.entrySet()) {
                for (Map.Entry<Character, List<Integer>> resultCharOrdered : resultOrdered.getValue().entrySet()) {

                    result.append(buildString(resultOrdered.getKey(), resultCharOrdered.getKey(), resultCharOrdered.getValue(), indexOfString));

                    result.append("/");
                }

            }

            if (result.lastIndexOf("/") > -1) {
                result.deleteCharAt(result.lastIndexOf("/"));
            }


            //for entradas en hash

                // for characters in map
                // if count > 1

                // add TreeSet -> final, key : count, value hashmap<character, arrayofindex)

            // for final count, hashmap
                // for keyEntry in hashmap
                    // if arrayofindex.size === indexOfString
                         // add =: + character * count
                    // else if arrayofindex.size > 1
                        // add arrayofindex.mkString(",") : + character * count
                    // else
                        // add arrayofindex.toString : + character * count
                    // Add Separator ("/")

        } catch (ClassCastException cce) {
            throw new RuntimeException(
                new StringBuffer()
                    .append("All object in array '")
                    .append(this.jsonKeyStrings)
                    .append("' must be a String object.")
                    .toString()
            );
        }


        return new JsonObject().put("result", result.toString());
    }

    private String buildString (final Integer numberOfChars, final Character character, final List<Integer> indexes, final Integer numberOfStrings) {
        final StringBuilder resultByCharAndCount = new StringBuilder();

        if (indexes.size() == numberOfStrings) {
            resultByCharAndCount.append("=");
        } else {
            resultByCharAndCount.append(StringUtils.join(indexes.iterator(), ","));
        }

        resultByCharAndCount.append(":");

        resultByCharAndCount.append(new String(new char[numberOfChars]).replace("\0", String.valueOf(character)));

        return resultByCharAndCount.toString();
    }
}
