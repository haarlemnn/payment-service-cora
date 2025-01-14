package br.med.nextmed.gateway.domain.services;

import br.med.nextmed.common.models.request.CancelPaymentRequest;
import br.med.nextmed.common.models.request.InitPaymentRequest;
import br.med.nextmed.common.models.request.RefundPaymentRequest;
import br.med.nextmed.common.models.request.StatusPaymentRequest;
import br.med.nextmed.common.models.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse initPayment(InitPaymentRequest initPaymentRequest);
    PaymentResponse statusPayment(StatusPaymentRequest statusPaymentRequest);
    void cancelPayment(CancelPaymentRequest cancelPaymentRequest);
    PaymentResponse refundPayment(RefundPaymentRequest refundPaymentRequest);

}
