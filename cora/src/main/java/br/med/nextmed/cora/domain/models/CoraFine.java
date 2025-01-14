package br.med.nextmed.cora.domain.models;

import lombok.Data;

@Data
public class CoraFine {

    private String date;
    private Integer amount;
    private Double rate;

}
