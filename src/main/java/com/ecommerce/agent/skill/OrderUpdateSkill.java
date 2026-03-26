package com.ecommerce.agent.skill;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ecommerce.agent.entity.Order;
import com.ecommerce.agent.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderUpdateSkill {
    private final OrderMapper orderMapper;

    public String updateOrderStatus(String orderNo, String status) {
        Order update = new Order();
        update.setStatus(status);
        int rows = orderMapper.update(update, new QueryWrapper<Order>().eq("order_no", orderNo));
        return rows > 0 ? "订单" + orderNo + "已修改为：" + status : "修改失败";
    }
}
