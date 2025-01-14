package br.med.nextmed.common.models.response;

import br.med.nextmed.common.enums.ErrorCode;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorResponse {

    private ErrorCode code;
    private String message;
    private Instant timestamp;
    private int status;
    private String path;

}
