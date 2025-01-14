package br.med.nextmed.cora.domain.models;

import br.med.nextmed.cora.domain.enums.CoraNotificationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoraChannel {

    private String channel;
    private String contact;
    private List<CoraNotificationType> rules;

}
