package br.med.nextmed.common.mappers;

import br.med.nextmed.common.models.Payment;

public interface PaymentRequestMapper {

    Object fromPayment(Payment payment);

}
