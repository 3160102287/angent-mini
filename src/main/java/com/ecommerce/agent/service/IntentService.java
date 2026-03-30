package com.ecommerce.agent.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntentService {

    private final ChatLanguageModel chatModel;

    public String recognize(String input) {
        String prompt = """
            你是电商客服意图识别器，只返回以下其中一个：
            QUERY_ORDER：查询订单、物流、状态
            UPDATE_ORDER：修改订单、取消订单、改地址
            UNKNOWN：其他问题
            
            用户：%s
            """.formatted(input);
        return chatModel.generate(prompt).trim();
    }
}
