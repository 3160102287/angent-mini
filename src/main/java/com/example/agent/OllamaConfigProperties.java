package com.example.agent;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 专门的配置类
 * 统一管理所有 ollama 相关配置
 * 企业项目标准写法
 */
@Component
@ConfigurationProperties(prefix = "ollama") // 绑定 yml 里 ollama.xxx
public class OllamaConfigProperties {

    // Ollama 地址
    private String baseUrl;

    // 模型名称
    private String modelName;

    // 温度
    private Double temperature;

    // ===================== getter & setter =====================
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
