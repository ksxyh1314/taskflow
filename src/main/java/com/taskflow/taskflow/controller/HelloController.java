package com.taskflow.taskflow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // 告诉Spring：这个类负责处理HTTP请求，返回JSON数据
public class HelloController {

    @GetMapping("/hello")  // 处理 GET /hello 这个请求
    public String hello() {
        return "TaskFlow 启动成功！";
    }
}