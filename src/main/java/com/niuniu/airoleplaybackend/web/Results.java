package com.niuniu.airoleplaybackend.web;


import com.niuniu.airoleplaybackend.errorcode.BaseErrorCode;
import com.niuniu.airoleplaybackend.exception.AbstractException;
import com.niuniu.airoleplaybackend.result.Result;

import java.util.Optional;

public class Results {

    /**
     * 构造成功相应
     */
    public static Result<Void> success() {
        return new Result<Void>()
                .setCode(Result.SUCCESS_CODE);
    }
    
    /**
     * 构造带返回数据的成功相应
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(Result.SUCCESS_CODE)
                .setData(data);
    }
    
    /**
     * 构建服务端失败响应
     */
    public static Result<Void> failure() {
        return new Result<Void>()
                .setCode(BaseErrorCode.CLIENT_ERROR.code())
                .setMessage(BaseErrorCode.CLIENT_ERROR.message());
    }
    
    /**
     * 通过 {@link AbstractException} 构建失败响应
     */
    public static Result<Void> failure(AbstractException abstractException){
        String code = Optional.ofNullable(abstractException.getErrorCode())
                .orElse(BaseErrorCode.SERVICE_ERROR.code());
        String message = Optional.ofNullable(abstractException.getErrorMessage())
                .orElse(BaseErrorCode.SERVICE_ERROR.message());
        
        return new Result<Void>()
                .setCode(code)
                .setMessage(message);
    }
    
    /**
     * 通过errorCode和errorMessage 构建失败响应
     */
    public static Result<Void> failure(String errorCode, String errorMessage) {
        return new Result<Void>()
                .setCode(errorCode)
                .setMessage(errorMessage);
    }

}
