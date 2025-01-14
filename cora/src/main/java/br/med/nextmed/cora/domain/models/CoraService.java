package br.med.nextmed.cora.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoraService {

    private String name;
    private String description;
    private Integer amount;

}
