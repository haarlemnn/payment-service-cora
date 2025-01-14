package br.med.nextmed.common.contracts;

import br.med.nextmed.common.enums.ProviderType;
import br.med.nextmed.common.exceptions.MethodNotImplementedException;
import br.med.nextmed.common.models.Payment;
import br.med.nextmed.common.models.response.PaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface PaymentProvider {

    default PaymentResponse authorize(Payment payment) throws JsonProcessingException {
        throw new MethodNotImplementedException();
    }
    default PaymentResponse capture(Payment payment) throws JsonProcessingException {
        throw new MethodNotImplementedException();
    }
    default void cancel(Payment payment) throws JsonProcessingException {
        throw new MethodNotImplementedException();
    }
    default PaymentResponse refund(Payment payment) throws JsonProcessingException {
        throw new MethodNotImplementedException();
    }
    default PaymentResponse status(Payment payment) throws JsonProcessingException {
        throw new MethodNotImplementedException();
    }
    ProviderType type();
    void setProviderSpecifics(Map<String, String> providerSpecifics);

}
