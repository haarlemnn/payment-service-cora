package br.med.nextmed.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

    @NotNull(message = "Customer 'name' is required")
    private String name;

    @NotNull(message = "Customer 'email' is required")
    private String email;

    @NotNull(message = "Customer 'phone' is required")
    private String phone;

    @NotNull(message = "Customer 'contact' is required")
    private List<CustomerContact> contacts;

    @NotNull(message = "Customer 'document' is required")
    private String document;

    @NotNull(message = "Customer 'documentType' is required")
    private String documentType;

    private CustomerAddress address;

}
