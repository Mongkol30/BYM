package com.bym.beeryyummymap.services;

import com.bym.beeryyummymap.dto.AiMenuResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AiService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public List<AiMenuResponseDto> extractMenuFromImage(String base64Image) throws Exception {
        if (apiKey == null || apiKey.isEmpty() || apiKey.equals("YOUR_GEMINI_API_KEY_HERE") || apiKey.equals("your_gemini_api_key")) {
            throw new RuntimeException("Gemini API Key is missing. Please configure it in application-dev.yml");
        }

        String cleanApiKey = apiKey.trim();
        // Use gemini-flash-lite-latest for the cheapest cost available on this API key
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-lite-latest:generateContent?key=" + cleanApiKey;

        String mimeType = "image/jpeg"; // We forced jpeg in frontend compressed string

        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> contents = new ArrayList<>();
        Map<String, Object> content = new HashMap<>();
        List<Map<String, Object>> parts = new ArrayList<>();

        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", "Extract the menu items and their prices from this image. Return strictly a JSON array of objects, where each object has 'name' (string), 'price' (number), and 'category' (string). For 'category', classify the item strictly into one of these exact string values: 'MAIN_DISH' (for main courses), 'TOPPING_SNACK' (for toppings, sides, add-ons, or snacks), or 'BEVERAGE' (for drinks). If a price is not found, use 0. Do not return any other markdown or text.");
        parts.add(textPart);

        Map<String, Object> imagePart = new HashMap<>();
        Map<String, Object> inlineData = new HashMap<>();
        inlineData.put("mimeType", mimeType);
        inlineData.put("data", base64Image);
        imagePart.put("inlineData", inlineData);
        parts.add(imagePart);

        content.put("parts", parts);
        contents.add(content);
        requestBody.put("contents", contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        String responseStr = restTemplate.postForObject(url, request, String.class);
        
        return parseGeminiResponse(responseStr);
    }

    private List<AiMenuResponseDto> parseGeminiResponse(String responseStr) throws Exception {
        JsonNode root = objectMapper.readTree(responseStr);
        String textResponse = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
        
        // Clean up possible markdown json formatting
        textResponse = textResponse.replaceAll("```json", "").replaceAll("```", "").trim();
        
        return objectMapper.readValue(textResponse, new TypeReference<List<AiMenuResponseDto>>(){});
    }

}
