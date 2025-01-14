package br.med.nextmed.gateway.domain.exceptions;

import br.med.nextmed.common.enums.ErrorCode;
import br.med.nextmed.common.exceptions.DefaultRuntimeException;

public class PaymentProviderNotFoundException extends DefaultRuntimeException {

    public PaymentProviderNotFoundException() {
        super("Provider not found", ErrorCode.PROVIDER_NOT_FOUND, 404);
    }

}
