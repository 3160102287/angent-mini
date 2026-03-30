package com.ecommerce.agent.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
public class OllamaConfig {

    @Bean
    @ConfigurationProperties(prefix = "ollama")
    public OllamaProperties ollamaProperties() {
        return new OllamaProperties();
    }

    @Bean
    public ChatLanguageModel chatLanguageModel(OllamaProperties prop) {
        return OllamaChatModel.builder()
                .baseUrl(prop.getBaseUrl())
                .modelName(prop.getModelName())
                .temperature(prop.getTemperature())
                .build();
    }

    @Data
    public static class OllamaProperties {
        private String baseUrl;
        private String modelName;
        private double temperature;
    }
}
