package br.med.nextmed.common.exceptions;

import br.med.nextmed.common.enums.ErrorCode;

public class CancelPaymentException extends DefaultRuntimeException {

    public CancelPaymentException() {
        super("Failed to cancel payment", ErrorCode.CANCEL_PAYMENT_FAILED);
    }

}
