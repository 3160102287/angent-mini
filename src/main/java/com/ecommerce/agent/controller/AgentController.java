package com.ecommerce.agent.controller;

import com.ecommerce.agent.service.AgentService;
import com.ecommerce.agent.service.IntentService;
import com.ecommerce.agent.skill.OrderQuerySkill;
import com.ecommerce.agent.skill.OrderUpdateSkill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/agent")
@Tag(name = "电商客服Agent")
@RequiredArgsConstructor
public class AgentController {
    private final AgentService agentService;
    private final IntentService intentService;
    private final OrderQuerySkill orderQuerySkill;
    private final OrderUpdateSkill orderUpdateSkill;

    @PostMapping("/chat")
    @Operation(summary = "AI对话")
    public Map<String, String> chat(@RequestBody Map<String, String> req) {
        return Map.of("answer", agentService.chat(req.get("input")));
    }

    @PostMapping("/intent")
    @Operation(summary = "AI意图识别")
    public Map<String, String> intent(@RequestBody Map<String, String> req) {
        return Map.of("intent", intentService.recognize(req.get("input")));
    }

    @PostMapping("/query")
    @Operation(summary = "查询订单")
    public Map<String, String> query(@RequestBody Map<String, String> req) {
        return Map.of("result", orderQuerySkill.queryOrder(req.get("orderNo")));
    }

    @PostMapping("/update")
    @Operation(summary = "修改订单")
    public Map<String, String> update(@RequestBody Map<String, String> req) {
        return Map.of("result", orderUpdateSkill.updateOrderStatus(req.get("orderNo"), "已发货"));
    }
}