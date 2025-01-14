package br.med.nextmed.cora.domain.enums;

import br.med.nextmed.common.enums.DiscountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public enum CoraDiscountType {

    FIXED("FIXED"),
    PERCENT("PERCENT");

    private final String discountType;

    CoraDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public static CoraDiscountType fromString(String discountType) {
        for (var value : CoraDiscountType.values()) {
            if (value.getDiscountType().equalsIgnoreCase(discountType)) {
                return value;
            }
        }
        return null;
    }


    public static boolean isValidDiscountType(String discountType) {
        return CoraDiscountType.fromString(discountType) != null;
    }

}
