package com.ecommerce.agent.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ecommerce.agent.entity.Order;
import com.ecommerce.agent.mapper.OrderMapper;
import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final ChatLanguageModel chatModel;

    private static final Pattern ORDER_NO_PATTERN = Pattern.compile("(\\d{6,})");

    // 查询订单
    public String queryOrder(String msg) {
        String orderNo = extractOrderNo(msg);
        Order order = orderMapper.selectOne(Wrappers.lambdaQuery(Order.class)
                .eq(Order::getOrderNo, orderNo).last("LIMIT 1"));

        if (order == null) return "未找到订单：" + orderNo;
        return chatModel.generate("""
            用客服语气简洁回复订单信息：%s
            """.formatted(order.toString()));
    }

    // 修改订单状态
    public String updateOrder(String msg) {
        String orderNo = extractOrderNo(msg);
        Order order = orderMapper.selectOne(Wrappers.lambdaQuery(Order.class)
                .eq(Order::getOrderNo, orderNo).last("LIMIT 1"));

        if (order == null) return "订单不存在";
        order.setStatus("已发货");
        orderMapper.updateById(order);
        return "订单：" + orderNo + " 已修改为【已发货】";
    }

    // 提取订单号
    private String extractOrderNo(String msg) {
        Matcher matcher = ORDER_NO_PATTERN.matcher(msg);
        return matcher.find() ? matcher.group(1) : "";
    }
}
