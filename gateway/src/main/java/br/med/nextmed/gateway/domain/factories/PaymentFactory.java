package br.med.nextmed.gateway.domain.factories;

import br.med.nextmed.common.models.Payment;
import br.med.nextmed.common.models.request.CancelPaymentRequest;
import br.med.nextmed.common.models.request.InitPaymentRequest;
import br.med.nextmed.common.models.request.RefundPaymentRequest;
import br.med.nextmed.common.models.request.StatusPaymentRequest;
import org.apache.commons.lang.StringUtils;

public class PaymentFactory {

    public static Payment fromInitPaymentRequest(InitPaymentRequest request, String requestId) {
        var paymentRequestId = StringUtils.isNotBlank(request.getRequestId()) ? request.getRequestId() : requestId;

        return Payment.builder()
            .requestId(paymentRequestId)
            .total(request.getAmount())
            .dueDate(request.getDueDate())
            .paymentMethod(request.getPaymentMethod())
            .customer(request.getCustomer())
            .discount(request.getDiscount())
            .name(request.getName())
            .description(request.getDescription())
            .build();
    }

    public static Payment fromCancelPaymentRequest(CancelPaymentRequest request) {
        return Payment.builder()
            .transactionId(request.getTransactionId())
            .requestId(request.getRequestId())
            .build();
    }

    public static Payment fromRefundPaymentRequest(RefundPaymentRequest request) {
        return Payment.builder()
            .transactionId(request.getTransactionId())
            .requestId(request.getRequestId())
            .build();
    }

    public static Payment fromStatusPaymentRequest(StatusPaymentRequest request) {
        return Payment.builder()
            .transactionId(request.getTransactionId())
            .requestId(request.getRequestId())
            .build();
    }

}
