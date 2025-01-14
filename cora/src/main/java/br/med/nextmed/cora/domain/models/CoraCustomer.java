package br.med.nextmed.cora.domain.models;

import lombok.Data;

@Data
public class CoraCustomer {

    private String name;
    private String email;
    private String telephone;
    private CoraCustomerDocument document;
    private CoraCustomerAddress address;

}
