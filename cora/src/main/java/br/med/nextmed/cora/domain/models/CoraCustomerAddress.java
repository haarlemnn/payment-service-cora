package br.med.nextmed.cora.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoraCustomerAddress {

    private String street;
    private String number;
    private String district;
    private String city;
    private String state;
    private String complement;
    private String country;

    @JsonProperty("zip_code")
    private String zipCode;

}
