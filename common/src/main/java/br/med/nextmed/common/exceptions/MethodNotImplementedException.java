package br.med.nextmed.common.exceptions;

import br.med.nextmed.common.enums.ErrorCode;

public class MethodNotImplementedException extends DefaultRuntimeException {

    public MethodNotImplementedException() {
        super("Method not implemented", ErrorCode.METHOD_NOT_IMPLEMENTED);
    }

}
