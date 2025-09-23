package com.niuniu.airoleplaybackend.exception;

import com.niuniu.airoleplaybackend.errorcode.BaseErrorCode;
import com.niuniu.airoleplaybackend.errorcode.IErrorCode;

/**
 * 客户端异常｜用户发起调用请求后因客户端提交参数或其他客户端问题导致的异常
 * <p>
 * 作者：
 * 开发时间：2025/5/21
 */
public class ClientException extends AbstractException {

    public ClientException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ClientException(String message) {
        this(message, null, BaseErrorCode.CLIENT_ERROR);
    }

    public ClientException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
