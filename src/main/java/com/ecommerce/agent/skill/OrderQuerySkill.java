package com.ecommerce.agent.skill;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ecommerce.agent.entity.Order;
import com.ecommerce.agent.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderQuerySkill {
    private final OrderMapper orderMapper;

    public String queryOrder(String orderNo) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_no", orderNo));
        if (order == null) {
            return "订单" + orderNo + "不存在";
        }

        return "【订单信息】" +
                " 订单号：" + order.getOrderNo() +
                " | 商品：" + order.getGoods() +
                " | 价格：" + order.getPrice() +
                " | 状态：" + order.getStatus() +
                " | 时间：" + order.getCreateTime();
    }
}