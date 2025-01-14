package br.med.nextmed.common.models.response;

import br.med.nextmed.common.enums.PaymentStatus;
import br.med.nextmed.common.models.Payment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private String details;
    private Payment payment;
    private PaymentStatus status;

}
