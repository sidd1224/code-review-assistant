package com.internship.codereview.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ExternalAPIConfig {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String baseUrl;

    @Bean
    public RestClient restClient() {

        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization","Bearer " + apiKey)
                .defaultHeader("Content-Type","application/json")
                .build();
    }
}
