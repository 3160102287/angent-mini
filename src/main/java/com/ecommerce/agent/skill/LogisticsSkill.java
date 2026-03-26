package com.ecommerce.agent.skill;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class LogisticsSkill {

    @Tool("根据订单号查询物流信息")
    public String queryLogistics(String orderNo) {
        return "订单号:" + orderNo +
                " | 快递:顺丰" +
                " | 状态:派送中";
    }
}
