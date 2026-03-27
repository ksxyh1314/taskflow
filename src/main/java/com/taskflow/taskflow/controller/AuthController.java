package com.taskflow.taskflow.controller;

import com.taskflow.taskflow.common.ApiResponse;
import com.taskflow.taskflow.dto.LoginRequest;        // ← 新增
import com.taskflow.taskflow.dto.RegisterRequest;
import com.taskflow.taskflow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 注册（原有，不动）
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok(ApiResponse.success("注册成功"));
    }

    // 登录（新增）
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @Valid @RequestBody LoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(token));
    }
}
