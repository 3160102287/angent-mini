package com.ecommerce.agent.controller;

import com.ecommerce.agent.dto.ChatRequest;
import com.ecommerce.agent.service.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @Operation(summary = "AI 智能对话（核心接口）")
    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        return agentService.chat(request.getMessage());
    }

    @Operation(summary = "意图识别")
    @PostMapping("/intent")
    public String intent(@RequestBody ChatRequest request) {
        return agentService.recognizeIntent(request.getMessage());
    }

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}