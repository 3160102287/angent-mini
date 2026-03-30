package com.ecommerce.agent.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final IntentService intentService;
    private final OrderService orderService;
    private final ChatLanguageModel chatModel;

    public String chat(String userMessage) {
        // 1. 识别意图
        String intent = intentService.recognize(userMessage);

        // 2. 根据意图执行动作
        return switch (intent) {
            case "QUERY_ORDER" -> orderService.queryOrder(userMessage);
            case "UPDATE_ORDER" -> orderService.updateOrder(userMessage);
            default -> chatModel.generate("用户问题：" + userMessage);
        };
    }

    public String recognizeIntent(String userMessage) {
        return intentService.recognize(userMessage);
    }
}
