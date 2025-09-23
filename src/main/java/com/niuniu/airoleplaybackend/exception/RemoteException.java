package com.niuniu.airoleplaybackend.exception;


import com.niuniu.airoleplaybackend.errorcode.BaseErrorCode;
import com.niuniu.airoleplaybackend.errorcode.IErrorCode;

/**
 * 远程调用异常
 * <p>
 * 作者：
 */
public class RemoteException extends AbstractException {

    public RemoteException(String message) {
        this(message, null, BaseErrorCode.REMOTE_ERROR);
    }

    public RemoteException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public RemoteException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "RemoteException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}

