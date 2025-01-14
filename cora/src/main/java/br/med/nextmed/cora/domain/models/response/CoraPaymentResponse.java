package br.med.nextmed.cora.domain.models.response;

import br.med.nextmed.cora.domain.enums.CoraPaymentStatus;
import br.med.nextmed.cora.domain.models.CoraCustomer;
import br.med.nextmed.cora.domain.models.CoraPayment;
import br.med.nextmed.cora.domain.models.CoraPaymentOptions;
import br.med.nextmed.cora.domain.models.CoraPaymentTerms;
import br.med.nextmed.cora.domain.models.CoraPix;
import br.med.nextmed.cora.domain.models.CoraService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CoraPaymentResponse {

    private String id;
    private CoraPaymentStatus status;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("total_amount")
    private Integer totalAmount;

    @JsonProperty("total_paid")
    private Integer totalPaid;

    @JsonProperty("occurrence_date")
    private String occurrenceDate;

    private String code;

    private CoraCustomer customer;
    private List<CoraService> services;

    @JsonProperty("payment_terms")
    private CoraPaymentTerms paymentTerms;

    @JsonProperty("payment_options")
    private CoraPaymentOptions paymentOptions;

    private List<CoraPayment> payments;
    private CoraPix pix;

}
