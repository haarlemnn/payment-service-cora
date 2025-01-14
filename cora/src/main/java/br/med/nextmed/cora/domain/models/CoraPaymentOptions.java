package br.med.nextmed.cora.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoraPaymentOptions {

    @JsonProperty("bank_slip")
    private CoraBankSlip bankSlip;

}
