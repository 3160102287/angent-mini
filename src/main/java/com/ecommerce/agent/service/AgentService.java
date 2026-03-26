package com.ecommerce.agent.service;

import com.ecommerce.agent.skill.OrderQuerySkill;
import com.ecommerce.agent.skill.OrderUpdateSkill;
import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {
    private final ChatLanguageModel chatModel;
    private final OrderQuerySkill orderQuerySkill;
    private final OrderUpdateSkill orderUpdateSkill;
    private final IntentService intentService;

    public String chat(String input) {
        try {
            String intent = intentService.recognize(input);
            String orderNo = extractOrderNo(input);
            String result = "";

            switch (intent) {
                case "QUERY_ORDER" -> result = orderQuerySkill.queryOrder(orderNo);
                case "UPDATE_ORDER" -> result = orderUpdateSkill.updateOrderStatus(orderNo, "已发货");
                default -> result = "我只处理订单查询和订单状态修改哦~";
            }

            String prompt = "你是电商客服，请友好简洁回复：%s".formatted(result);
            return chatModel.generate(prompt);
        } catch (Exception e) {
            return "系统异常：" + e.getMessage();
        }
    }

    private String extractOrderNo(String input) {
        var matcher = java.util.regex.Pattern.compile("\\d{5,16}").matcher(input);
        return matcher.find() ? matcher.group() : "unknown";
    }
}
