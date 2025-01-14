package br.med.nextmed.common.utils;

import br.med.nextmed.common.models.Payment;
import org.slf4j.MDC;

public class MDCUtils {

    public static void populateMDCWithPayment(Payment payment) {
        MDC.put("transactionId", payment.getTransactionId());
        MDC.put("requestId", payment.getRequestId());

        if (payment.getPaymentMethod() != null) {
            MDC.put("paymentMethod", payment.getPaymentMethod().name());
        }
    }

}
