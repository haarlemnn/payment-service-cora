package br.med.nextmed.cora.infrastructure.mappers;

import br.med.nextmed.common.enums.PaymentMethod;
import br.med.nextmed.common.enums.PaymentStatus;
import br.med.nextmed.common.mappers.PaymentResponseMapper;
import br.med.nextmed.common.models.Customer;
import br.med.nextmed.common.models.Payment;
import br.med.nextmed.common.models.response.PaymentResponse;
import br.med.nextmed.cora.domain.enums.CoraPaymentStatus;
import br.med.nextmed.cora.domain.models.CoraCustomer;
import br.med.nextmed.cora.domain.models.response.CoraPaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CoraPaymentResponseMapper implements PaymentResponseMapper {

    @Override
    public PaymentResponse toPaymentResponse(Payment payment, Object providerPaymentResponse, String requestId) throws JsonProcessingException {
        var coraPaymentResponse = (CoraPaymentResponse) providerPaymentResponse;
        var responseJson = this.detailsToJson(coraPaymentResponse);

        var remainingAmount = coraPaymentResponse.getTotalAmount() - coraPaymentResponse.getTotalPaid();

        var createdPayment = Payment.builder()
            .dueDate(coraPaymentResponse.getPaymentTerms().getDueDate())
            .total(BigDecimal.valueOf(coraPaymentResponse.getTotalAmount()).movePointLeft(2))
            .paid(BigDecimal.valueOf(coraPaymentResponse.getTotalPaid()).movePointLeft(2))
            .remaining(BigDecimal.valueOf(remainingAmount).movePointLeft(2))
            .paymentMethod(PaymentMethod.BANK_SLIP)
            .transactionId(coraPaymentResponse.getId())
            .requestId(requestId)
            .customer(getCustomer(payment.getCustomer(), coraPaymentResponse.getCustomer()))
            .build();

        return PaymentResponse.builder()
            .details(responseJson)
            .payment(createdPayment)
            .status(getPaymentStatus(coraPaymentResponse.getStatus()))
            .build();
    }

    private Customer getCustomer(Customer customer, CoraCustomer coraCustomer) {
        if (customer != null) {
            return customer;
        }

        return Customer.builder()
            .name(coraCustomer.getName())
            .email(coraCustomer.getEmail())
            .phone(coraCustomer.getTelephone())
            .document(coraCustomer.getDocument().getIdentity())
            .documentType(coraCustomer.getDocument().getType())
            .build();
    }

    private PaymentStatus getPaymentStatus(CoraPaymentStatus coraPaymentStatus) {
        switch (coraPaymentStatus) {
            case OPEN, DRAFT -> {
                return PaymentStatus.PENDING;
            }
            case IN_PAYMENT -> {
                return PaymentStatus.PROCESSING;
            }
            case PAID -> {
                return PaymentStatus.SUCCESS;
            }
            case CANCELLED -> {
                return PaymentStatus.CANCELLED;
            }
            default -> {
                return PaymentStatus.FAILURE;
            }
        }
    }

}
