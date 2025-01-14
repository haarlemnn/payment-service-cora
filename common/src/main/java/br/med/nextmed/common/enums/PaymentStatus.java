package br.med.nextmed.common.enums;

public enum PaymentStatus {

    PENDING(false),
    PROCESSING(false),
    SUCCESS(true),
    CANCELLED(false),
    REFUNDED(false),
    PARTIAL_REFUNDED(false),
    REJECTED(false),
    FAILURE(false);

    private final boolean success;

    PaymentStatus(boolean success) {
        this.success = success;
    }

    public boolean success() {
        return this.success;
    }

}
