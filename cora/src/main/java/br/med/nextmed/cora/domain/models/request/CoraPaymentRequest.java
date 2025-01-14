package br.med.nextmed.cora.domain.models.request;

import br.med.nextmed.cora.domain.enums.CoraPaymentMethod;
import br.med.nextmed.cora.domain.models.CoraCustomer;
import br.med.nextmed.cora.domain.models.CoraNotification;
import br.med.nextmed.cora.domain.models.CoraPaymentTerms;
import br.med.nextmed.cora.domain.models.CoraService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CoraPaymentRequest {

    private CoraCustomer customer;
    private List<CoraService> services;

    private CoraNotification notification;

    @JsonProperty("payment_terms")
    private CoraPaymentTerms paymentTerms;

    @JsonProperty("payment_forms")
    private List<CoraPaymentMethod> paymentForms;

}
