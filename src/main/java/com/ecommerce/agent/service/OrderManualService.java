package com.ecommerce.agent.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ecommerce.agent.entity.Order;
import com.ecommerce.agent.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderManualService {

    private final OrderMapper orderMapper;

    // 新增订单
    public String createOrder(Order order) {
        order.setCreateTime(LocalDateTime.now());
        orderMapper.insert(order);
        return "订单创建成功：" + order.getOrderNo();
    }

    // 根据订单号查询
    public Order getByOrderNo(String orderNo) {
        return orderMapper.selectOne(Wrappers.lambdaQuery(Order.class)
                .eq(Order::getOrderNo, orderNo)
                .last("LIMIT 1"));
    }

    // 查询所有
    public List<Order> listAll() {
        return orderMapper.selectList(Wrappers.emptyWrapper());
    }

    // 修改状态
    public String updateOrderStatus(String orderNo, String status) {
        Order order = getByOrderNo(orderNo);
        if (order == null) {
            return "订单不存在";
        }

        order.setStatus(status);
        orderMapper.updateById(order);
        return "订单" + orderNo + " 已更新为：" + status;
    }

    // 删除订单
    public String deleteOrder(String orderNo) {
        Order order = getByOrderNo(orderNo);
        if (order == null) {
            return "订单不存在";
        }

        orderMapper.deleteById(order.getId());
        return "订单" + orderNo + " 已删除";
    }
}
