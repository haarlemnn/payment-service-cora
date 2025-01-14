package br.med.nextmed.common.models;

import br.med.nextmed.common.enums.DiscountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Discount {

    @NotNull(message = "Discount 'amount' is required")
    private BigDecimal amount;

    @NotNull(message = "Discount 'type' is required")
    private DiscountType type;

    @JsonIgnore
    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

}
