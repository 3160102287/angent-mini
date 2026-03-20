package com.example.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

// Spring Boot 启动类
@SpringBootApplication
public class Qwen3SpringBootAgentDemo {

    // 1. 注入 RestTemplate（Spring Boot 推荐的 HTTP 客户端）
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    // 2. 业务 Agent 接口（保持不变）
    public interface LLMBusinessAgent {
        String callQwen3(String prompt);
    }

    // 3. Spring Bean 化的 Agent 实现类（对接 Ollama）
    @org.springframework.stereotype.Component // 交给 Spring 容器管理
    public static class Qwen3BusinessAgentImpl implements LLMBusinessAgent {
        private final RestTemplate restTemplate;
        private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";
        private static final String MODEL_NAME = "qwen3:8b";

        // 构造器注入 RestTemplate
        public Qwen3BusinessAgentImpl(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        @Override
        public String callQwen3(String prompt) {
            // 1. 构造请求头（JSON 格式）
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 2. 构造请求体（Ollama 参数）
            OllamaRequest request = new OllamaRequest(MODEL_NAME, prompt, false);
            HttpEntity<OllamaRequest> httpEntity = new HttpEntity<>(request, headers);

            // 3. 调用 Ollama API 并解析响应
            OllamaResponse response = restTemplate.postForObject(OLLAMA_API_URL, httpEntity, OllamaResponse.class);
            return response != null ? response.getResponse() : "调用失败";
        }

        // 辅助类：请求参数
        public static class OllamaRequest {
            private String model;
            private String prompt;
            private boolean stream;

            public OllamaRequest(String model, String prompt, boolean stream) {
                this.model = model;
                this.prompt = prompt;
                this.stream = stream;
            }

            // getter/setter（Spring JSON 解析需要）
            public String getModel() { return model; }
            public String getPrompt() { return prompt; }
            public boolean isStream() { return stream; }
        }

        // 辅助类：响应结果
        public static class OllamaResponse {
            private String response;
            private boolean done;

            // getter/setter
            public String getResponse() { return response; }
            public boolean isDone() { return done; }
        }
    }

    // 4. 测试入口（Spring Boot 启动后执行）
    public static void main(String[] args) {
        // 启动 Spring 容器
        var context = SpringApplication.run(Qwen3SpringBootAgentDemo.class, args);
        // 从容器中获取 Agent Bean
        LLMBusinessAgent agent = context.getBean(Qwen3BusinessAgentImpl.class);

        // 调用 Qwen3:8B
        String prompt = "用Spring Boot实现业务Agent的核心优势是什么？";
        String answer = agent.callQwen3(prompt);
        System.out.println("提问：" + prompt);
        System.out.println("回答：" + answer);
    }
}