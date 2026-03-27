package com.taskflow.taskflow.common;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    // 成功
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data);
    }

    // 失败
    public static <T> ApiResponse<T> error(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
}
