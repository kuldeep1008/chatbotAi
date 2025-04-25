package com.deep.chatbotAi.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OpenAIService {

    @Value("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")
    private String apiKey;

    public String getAIResponse(String prompt) {
        try {
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("messages", new JSONArray().put(message));
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 150);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + "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")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("🟢 Status: " + response.statusCode());
            System.out.println("🟢 Raw JSON: " + response.body());

            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());
                return json.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim();
            } else {
                return "❌ OpenAI API error: " + response.statusCode();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, I couldn’t reach my brain (OpenAI) 😓";
        }
    }
}