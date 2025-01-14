package br.med.nextmed.common.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerAddress {

    @NotNull(message = "CustomerAddress 'address' is required")
    private String address;

    @NotNull(message = "CustomerAddress 'address2' is required")
    private String address2;

    @NotNull(message = "CustomerAddress 'country' is required")
    private String country;

    @NotNull(message = "CustomerAddress 'city' is required")
    private String city;

    @NotNull(message = "CustomerAddress 'state' is required")
    private String state;

    @NotNull(message = "CustomerAddress 'zipCode' is required")
    private String zipCode;

    @NotNull(message = "CustomerAddress 'number' is required")
    private String number;

}
