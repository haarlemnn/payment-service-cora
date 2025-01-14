package br.med.nextmed.cora.domain.models;

import br.med.nextmed.cora.domain.enums.CoraDiscountType;
import lombok.Data;

@Data
public class CoraDiscount {

    private CoraDiscountType type;
    private Double value;

}
