package br.med.nextmed.cora.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoraBankSlip {

    private String barcode;
    private String digitable;
    private String registered;
    private String url;

    @JsonProperty("our_number")
    private String ourNumber;

}
