package com.deep.chatbotAi.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class GoogleSearchService {


    @Value("xxxxxxxxxxxxxxx1")
    private String apiKey;

    @Value("\n" +
            "https://cse.google.com/cse?cx=xxxx")
    private String searchEngineId;

    public String search(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = String.format("https://www.googleapis.com/customsearch/v1?q=%s&key=%s&cx=%s",
                    encodedQuery, "764e72e1c1f3243c1", "\n" +
                            "https://cse.google.com/cse?cx=764e72e1c1f3243c1");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());
            JSONArray items = json.getJSONArray("items");

            StringBuilder result = new StringBuilder("🔍 Google Search Results:\n\n");

            for (int i = 0; i < Math.min(3, items.length()); i++) {
                JSONObject item = items.getJSONObject(i);
                result.append("• ").append(item.getString("title")).append("\n");
                result.append(item.getString("link")).append("\n\n");
            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Couldn't fetch results from Google.";
        }
    }
}
