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
    public ResponseDto analyze(RequestDto requestDto){
        String finalPrompt= promptUtil.getPromptTemplate()+requestDto.getCode();
        Map<String,Object> requestBody=Map.of(
                "model","gpt-4o-mini",
                "messages",new Object[]{
                        Map.of("role", "user", "content", finalPrompt)
                }
                ,"temperature", 0.2,
                "response_format",Map.of("type","json_object")
                );
        String rawJsonResponse=restClient.post()
                .uri("/chat/completions/")
                .body(requestBody)
                .retrieve()
                .body(String.class);
        String content = extractChoicesFromAIResponse(rawJsonResponse);
        try {
            // readValue takes the JSON string and maps it to the fields in your ResponseDto.java
            return objectMapper.readValue(content, ResponseDto.class);
        } catch (Exception e) {
            // If the AI returns malformed JSON or missing fields, this catch block handles the error
            throw new RuntimeException("Error parsing AI JSON response", e);
        }

    }
    public String extractChoicesFromAIResponse(String rawJsonResponse){
    try{
        JsonNode root= objectMapper.readTree(rawJsonResponse);
        return root.path("choices").get(0).path("message").path("content").asString();
    }catch(Exception e){
        throw new RuntimeException("Failed to Extract AI response"+e);
    }
    }
}
