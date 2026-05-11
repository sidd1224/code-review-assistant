package com.internship.codereview.service;

import com.internship.codereview.dto.RequestDto;
import com.internship.codereview.dto.ResponseDto;
import com.internship.codereview.util.PromptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class AnalyzeService {
    private final PromptUtil promptUtil;
    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    public AnalyzeService(ObjectMapper objectMapper,RestClient restClient,PromptUtil promptUtil) {
        this.objectMapper = objectMapper;
        this.restClient=restClient;
        this.promptUtil=promptUtil;
    }
    public ResponseDto analyze(RequestDto requestDto) {
        // 1. Build the Gemini-style prompt
        String instructions = promptUtil.getPromptTemplate();
        String codeToReview = requestDto.getCode();

        // 2. Updated Request Body with 'system_instruction'
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", codeToReview) // User sends ONLY the code
                        })
                },
                "system_instruction", Map.of( // Dedicated area for rules
                        "parts", Map.of("text", instructions)
                ),
                "generationConfig", Map.of(
                        "responseMimeType", "application/json",
                        "temperature", 0.1 // Lower temperature is better for code review
                )
        );

        // 3. Update the endpoint (Ensure your config uses the Gemini Base URL)
        String rawJsonResponse = restClient.post()
                .uri("/models/gemini-1.5-flash:generateContent")
                .body(requestBody)
                .retrieve()
                .body(String.class);

        String content = extractChoicesFromAIResponse(rawJsonResponse);

        try {
            return objectMapper.readValue(content, ResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing Gemini JSON response", e);
        }
    }

    public String extractChoicesFromAIResponse(String rawJsonResponse) {
        try {
            JsonNode root = objectMapper.readTree(rawJsonResponse);
            // 4. Update the JSON path: candidates -> content -> parts -> text
            return root.path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text").asString(); // Changed .asString() to .asText()
        } catch (Exception e) {
            throw new RuntimeException("Failed to Extract Gemini response: " + e.getMessage());
        }
    }
}
