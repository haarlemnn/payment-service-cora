package br.med.nextmed.common.exceptions;

import br.med.nextmed.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public class DefaultRuntimeException extends RuntimeException {

    private int statusCode = 500;
    private ErrorCode errorCode = ErrorCode.UNEXPECTED_ERROR;

    public DefaultRuntimeException(String message) {
        super(message);
    }

    public DefaultRuntimeException(Throwable throwable) {
        super(ErrorCode.UNEXPECTED_ERROR.name(), throwable);
    }

    public DefaultRuntimeException(String message, Throwable throwable) {
        super(message);
    }

    public DefaultRuntimeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DefaultRuntimeException(String message, ErrorCode errorCode, int statusCode) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

}
