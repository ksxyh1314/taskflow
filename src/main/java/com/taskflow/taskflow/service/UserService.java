package com.taskflow.taskflow.service;

import com.taskflow.taskflow.common.BusinessException;
import com.taskflow.taskflow.dto.LoginRequest;        // ← 新增
import com.taskflow.taskflow.dto.RegisterRequest;
import com.taskflow.taskflow.entity.User;
import com.taskflow.taskflow.repository.UserRepository;
import com.taskflow.taskflow.util.JwtUtil;            // ← 新增
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;                    // ← 新增

    // 注册（原有，不动）
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException(400, "用户已存在");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(400, "邮箱已被注册");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    // 登录（新增）
    public String login(LoginRequest request) {
        // 第一步：查用户是否存在
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(400, "用户不存在"));

        // 第二步：验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(400, "密码错误");
        }

        // 第三步：生成 Token 返回
        return jwtUtil.generateToken(user.getUsername());
    }
}
