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
            你是电商客服助手，只能返回以下其中一个：
            QUERY_ORDER 或 UPDATE_ORDER 或 UNKNOWN
            用户：%s
            """.formatted(input);
        return chatModel.generate(prompt).trim();
    }
}
