package br.med.nextmed.gateway.infrastructure.factories;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Component
public class RequestIdFactory {

    public String generate(String key) {
        Instant timestamp = Instant.now();
        String transactionId = String.format("%s%s", timestamp, key);

        byte[] encodedBytes = Base64.getUrlEncoder().withoutPadding().encode(transactionId.getBytes(StandardCharsets.UTF_8));
        String base64Encoded = new String(encodedBytes, StandardCharsets.UTF_8);

        return base64Encoded.substring(0, Math.min(35, base64Encoded.length()));
    }

    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
