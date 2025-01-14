package br.med.nextmed.cora.domain.exceptions;

import br.med.nextmed.common.exceptions.DefaultRuntimeException;

public class CoraHttpClientException extends DefaultRuntimeException {

    public CoraHttpClientException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }

}
