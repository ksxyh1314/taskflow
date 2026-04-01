package com.taskflow.taskflow.service;

import com.taskflow.taskflow.common.BusinessException;
import com.taskflow.taskflow.dto.LoginRequest;
import com.taskflow.taskflow.dto.RegisterRequest;
import com.taskflow.taskflow.entity.User;
import com.taskflow.taskflow.repository.UserRepository;
import com.taskflow.taskflow.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;  // ← 改这里
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // ← 改这里
    private final JwtUtil jwtUtil;

    // 注册
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

    // 登录
    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(400, "用户不存在"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(400, "密码错误");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}
