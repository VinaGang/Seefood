package com.example.seefood;

import java.util.Map;
import com.google.gson.JsonArray;

/***
 * Google Search Results using SerpApi
 *
 * Usage
 * ---
 * <pre>
 * {@code
 * Map<String, String> parameter = new HashMap<>();
 * parameter.put("q", "Coffee");
 * GoogleSearch google = new GoogleSearch(parameter, "secret api key");
 * JsonObject data = google.getJson();
 * JsonArray organic_results = data.get("organic_results").getAsJsonArray();
 * }
 * </pre>
 */
public class GoogleSearch extends SerpApiSearch {

    public static final String SERP_API_KEY_NAME = "5869f12410008dd7dfe38ba1f1d683579d4303e0b21649270f3257f53532a0d8";

    /**
     * Constructor
     * @param parameter search parameter
     * @param apiKey secret API key
     */
    public GoogleSearch(Map<String, String> parameter, String apiKey) {
        super(parameter, apiKey, "google");
    }

    /**
     * Constructor
     */
    public GoogleSearch() {
        super("google");
    }

    /**
     * Constructor
     * @param parameter search parameter
     */
    public GoogleSearch(Map<String, String> parameter) {
        super(parameter, "google");
    }

// end
}