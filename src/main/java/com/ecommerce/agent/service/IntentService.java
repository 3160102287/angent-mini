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
                你是电商客服意图识别器。
                用户输入只会属于以下三类：
                                
                1. QUERY_ORDER：查询订单、查物流、查状态
                2. UPDATE_ORDER：修改订单、取消订单、改地址
                3. UNKNOWN：其他问题
                                
                请严格只输出关键词，不要解释。
                用户：%s
                """.formatted(input);
        return chatModel.generate(prompt).trim();
    }
}
