package com.ecommerce.agent.controller;

import com.ecommerce.agent.entity.Order;
import com.ecommerce.agent.service.OrderManualService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderManualService orderManualService;

    @Operation(summary = "【手动】新增订单")
    @PostMapping("/create")
    public String create(@RequestBody Order order) {
        return orderManualService.createOrder(order);
    }

    @Operation(summary = "【手动】根据订单号查询")
    @GetMapping("/get")
    public Order getByOrderNo(@RequestParam String orderNo) {
        return orderManualService.getByOrderNo(orderNo);
    }

    @Operation(summary = "【手动】查询所有订单")
    @GetMapping("/list")
    public List<Order> list() {
        return orderManualService.listAll();
    }

    @Operation(summary = "【手动】修改订单状态")
    @PostMapping("/update")
    public String updateStatus(
            @RequestParam String orderNo,
            @RequestParam String status) {
        return orderManualService.updateOrderStatus(orderNo, status);
    }

    @Operation(summary = "【手动】删除订单")
    @PostMapping("/delete")
    public String delete(@RequestParam String orderNo) {
        return orderManualService.deleteOrder(orderNo);
    }
}
