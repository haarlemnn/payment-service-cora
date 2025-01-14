package br.med.nextmed.common.mappers;

import br.med.nextmed.common.models.Payment;
import br.med.nextmed.common.models.response.PaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface PaymentResponseMapper {

    PaymentResponse toPaymentResponse(Payment payment, Object providerPaymentResponse, String requestId) throws JsonProcessingException;

    default String detailsToJson(Object details) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(details);
    }

}
