package br.med.nextmed.common.models.request;

import br.med.nextmed.common.enums.PaymentMethod;
import br.med.nextmed.common.models.Customer;
import br.med.nextmed.common.models.Discount;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitPaymentRequest {

    @NotNull(message = "PaymentRequest 'amount' is required")
    private BigDecimal amount;

    @NotNull(message = "PaymentRequest 'dueDate' is required")
    private LocalDate dueDate;

    @NotNull(message = "PaymentRequest 'paymentMethod' is required")
    private PaymentMethod paymentMethod;

    @NotNull(message = "PaymentRequest 'customer' is required")
    private Customer customer;

    @NotNull(message = "PaymentRequest 'providerSpecifics' is required")
    private Map<String, String> providerSpecifics;

    private String name;
    private String description;
    private Discount discount;

    private String requestId;

}
