package com.niuniu.airoleplaybackend.web;


import cn.hutool.core.util.StrUtil;
import com.niuniu.airoleplaybackend.errorcode.BaseErrorCode;
import com.niuniu.airoleplaybackend.exception.AbstractException;
import com.niuniu.airoleplaybackend.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

/**
 * 全局异常拦截器
 * <p>
 * 作者：
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截参数异常验证
     */
    @SneakyThrows
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result validExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String exceptionStr = Optional.ofNullable(fieldError)
                .map(FieldError::getDefaultMessage)
                .orElse(StrUtil.EMPTY);
        log.error("[{}] {} [ex] {}", request.getMethod(), getUrl(request), exceptionStr);
        return Results.failure(BaseErrorCode.CLIENT_ERROR.code(), exceptionStr);
    }

    /**
     * 拦截业务内异常
     */
    @ExceptionHandler(value = AbstractException.class)
    public Result abstractExceptionHandler(HttpServletRequest request, AbstractException ex) {
        if (ex.getCause() != null) {
            log.error("[{}] {} [ex] {}", request.getMethod(), getUrl(request), ex, ex.getCause());
            return Results.failure(ex);
        }
        StringBuilder stackTraceBuilder = new StringBuilder();
        stackTraceBuilder.append(ex.getClass().getName()).append(":").append(ex.getMessage()).append("\n");
        StackTraceElement[] stackTrace = ex.getStackTrace();
        for (int i=0; i < Math.min(5, stackTrace.length); i++) {
            stackTraceBuilder.append("\tat ").append(stackTrace[i]).append("\n");
        }
        log.error("[{}] {} [ex] {} \n\n{}", request.getMethod(), getUrl(request), ex, stackTraceBuilder);
        return Results.failure(ex);
    }

    /**
     * 拦截未捕获异常
     */
    @ExceptionHandler(value = Throwable.class)
    public Result defaultErrorHandler(HttpServletRequest request, Throwable throwable) {
        log.error("[{}] {}", request.getMethod(), getUrl(request), throwable);
        return Results.failure();
    }
    private String getUrl(HttpServletRequest request) {
        if (StrUtil.isEmpty(request.getQueryString())){
            return request.getRequestURL().toString();
        }
        else{
            return request.getRequestURL().toString() + "?" + request.getQueryString();
        }
    }
}
