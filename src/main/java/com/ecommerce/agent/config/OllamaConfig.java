package com.ecommerce.agent.config;

import dev.langchain4j.model.ollama.OllamaChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OllamaConfig {
    private final OllamaProperties prop;

    @Bean
    public OllamaChatModel ollamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl(prop.getBaseUrl())
                .modelName(prop.getModelName())
                .temperature(prop.getTemperature())
                .build();
    }
}
