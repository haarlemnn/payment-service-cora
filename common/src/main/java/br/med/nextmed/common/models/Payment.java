package br.med.nextmed.common.models;

import br.med.nextmed.common.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private BigDecimal total;
    private BigDecimal paid;
    private BigDecimal remaining;

    private PaymentMethod paymentMethod;

    private String transactionId;
    private String requestId;
    private String name;
    private String description;

    private Customer customer;
    private Discount discount;

}
