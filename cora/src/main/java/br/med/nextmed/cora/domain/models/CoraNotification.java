package br.med.nextmed.cora.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoraNotification {

    private String name;
    private List<CoraChannel> channels;

}
