/*package com.taskflow.taskflow.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    //处理参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handValidationEception(
            MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String field=((FieldError)error).getField();
                String message=error.getDefaultMessage();
                errors.put(field,message);
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    // 处理业务异常（我们自己抛出的 RuntimeException）
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(
            RuntimeException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}*/


/*
package com.taskflow.taskflow.config;

import com.taskflow.taskflow.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, "参数校验失败", errors));
    }

    // ✅ 业务异常
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(
            RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, ex.getMessage(), null));
    }
}
*/

package com.taskflow.taskflow.config;

import com.taskflow.taskflow.common.ApiResponse;
import com.taskflow.taskflow.common.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 1. 参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, "参数校验失败", errors));
    }

    // ✅ 2. 业务异常（我们自己抛的 BusinessException）
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(
            BusinessException ex) {

        return ResponseEntity
                .status(ex.getCode())              // 动态状态码 404/403/400
                .body(ApiResponse.error(ex.getCode(), ex.getMessage(), null));
    }

    // ✅ 3. 兜底异常（系统级别未知错误）
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(
            Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "服务器内部错误", null));
    }
}
