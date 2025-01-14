package br.med.nextmed.common.exceptions;

import br.med.nextmed.common.enums.ErrorCode;

public class CreatePaymentException extends DefaultRuntimeException {

    public CreatePaymentException() {
        super("Failed to create payment", ErrorCode.CREATE_PAYMENT_FAILED);
    }

}
