package br.med.nextmed.common.exceptions;

import br.med.nextmed.common.enums.ErrorCode;

public class GetPaymentStatusException extends DefaultRuntimeException {

    public GetPaymentStatusException() {
        super("Failed to get payment status", ErrorCode.GET_PAYMENT_STATUS_FAILED);
    }

}
