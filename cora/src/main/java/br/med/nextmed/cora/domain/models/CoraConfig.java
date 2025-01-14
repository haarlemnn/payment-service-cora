package br.med.nextmed.cora.domain.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoraConfig {

    private String baseUrl;
    private String clientId;
    private String grantType;

    private String certificate; // Base64-encoded PEM certificate
    private String privateKey;  // Base64-encoded PEM private key

}
