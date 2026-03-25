package com.example.agent;

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Java 业务 Agent 主流方案：LangChain4j + Ollama + Spring Boot
 * 这是企业级标准开发模式
 */
@SpringBootApplication
public class LangChain4jBusinessAgentDemo {

    @Value("${ollama.base-url}")
    private String baseUrl;

    // ===================== 1. 配置 Ollama 模型（核心） =====================
    @Bean
    public OllamaChatModel ollamaChatModel(OllamaConfigProperties prop) {
//        System.out.println(baseUrl);
        return OllamaChatModel.builder()
                .baseUrl(prop.getBaseUrl())
                .modelName(prop.getModelName())
                .temperature(prop.getTemperature())
                .build();
    }

    // ===================== 2. 定义【业务 Agent 接口】（最关键） =====================
    /**
     * 业务 Agent 接口
     * LangChain4j 会自动实现这个接口！！
     * 你只需要定义方法 + 加注解
     */
    public interface BusinessAgent {

        // 给AI设定身份：业务专家Agent
        @SystemMessage("""
                你是一个专业的企业级业务智能助手Agent。
                你擅长用简洁、专业、可落地的语言回答业务问题。
                """)
        // 用户提问
        @UserMessage("{{it}}")
        String chat(String userMessage);
    }

    // ===================== 3. 交给Spring管理 =====================
    @Component
    public static class BusinessAgentProvider {
        // LangChain4j 自动生成 Agent 实现类
        @Bean
        public BusinessAgent businessAgent(OllamaChatModel model) {
            return AiServices.create(BusinessAgent.class, model);
        }
    }

    // ===================== 4. 启动测试 =====================
    public static void main(String[] args) {
        var context = SpringApplication.run(LangChain4jBusinessAgentDemo.class, args);


        // 获取业务Agent
        BusinessAgent agent = context.getBean(BusinessAgent.class);

        // 测试业务提问
        String question = "用Spring Boot实现业务Agent的核心优势是什么？";
        String answer = agent.chat(question);

        System.out.println("===== 业务Agent 问答 =====");
        System.out.println("提问：" + question);
        System.out.println("回答：" + answer);
    }
}