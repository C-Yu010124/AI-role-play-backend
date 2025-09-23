package com.niuniu.airoleplaybackend.exception;

import com.niuniu.airoleplaybackend.errorcode.BaseErrorCode;
import com.niuniu.airoleplaybackend.errorcode.IErrorCode;

/**
 * 服务类错误异常
 * <p>
 * 作者：
 * 开发时间：2025/5/21
 */
public class ServiceException extends AbstractException {

    public ServiceException(String message) {
        this(message, null, BaseErrorCode.SERVICE_ERROR);
    }

    public ServiceException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ServiceException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ServiceException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ServiceException{:" +
                "code:='" + errorCode + "'+" +
                "message='" + errorMessage + "'" +
                '}';
    }
}



