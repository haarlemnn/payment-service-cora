package br.med.nextmed.cora.domain.models;

import br.med.nextmed.cora.domain.enums.CoraPaymentMethod;
import br.med.nextmed.cora.domain.enums.CoraPaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoraPayment {

    private String id;
    private CoraPaymentStatus status;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("finalized_at")
    private String finalizedAt;

    @JsonProperty("total_paid")
    private Integer totalPaid;

    private CoraPaymentMethod method;

    private Integer interest;
    private Integer fine;

}
