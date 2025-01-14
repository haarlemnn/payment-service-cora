package br.med.nextmed.common.models.request;

import br.med.nextmed.common.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundPaymentRequest {

    @NotNull(message = "RefundPaymentRequest 'transactionId' is required")
    private String transactionId;
    private String requestId;
    private PaymentMethod paymentMethod;

    @NotNull(message = "RefundPaymentRequest 'providerSpecifics' is required")
    private Map<String, String> providerSpecifics;

}
