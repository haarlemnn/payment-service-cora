package br.med.nextmed.cora.infrastructure.http;

import br.med.nextmed.common.models.Payment;
import br.med.nextmed.cora.domain.models.CoraConfig;
import br.med.nextmed.cora.domain.models.request.CoraPaymentRequest;
import br.med.nextmed.cora.domain.models.response.CoraAuthResponse;
import br.med.nextmed.cora.domain.models.response.CoraPaymentResponse;
import br.med.nextmed.cora.infrastructure.http.client.CoraHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CoraAPIService {

    private static final String AUTH_ENDPOINT = "/token";
    private static final String CREATE_PAYMENT_ENDPOINT = "/v2/invoices";
    private static final String HANDLE_PAYMENT_ENDPOINT = "/v2/invoices/{invoice_id}";

    private static final String IDEMPOTENCY_KEY_HEADER = "Idempotency-Key";

    private static ThreadLocal<RestTemplate> threadLocalClient = ThreadLocal.withInitial(() -> null);

    public CoraAuthResponse getAuthToken(CoraConfig coraConfig) {
        log.info("Fetching CORA auth token...");

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", coraConfig.getGrantType());
        requestBody.add("client_id", coraConfig.getClientId());

        var httpEntity = new HttpEntity<>(requestBody, headers);

        var client = this.getClient(coraConfig);
        var response = client.postForEntity(AUTH_ENDPOINT, httpEntity, CoraAuthResponse.class);

        log.info("Fetching CORA auth token...OK");
        return response.getBody();
    }

    public CoraPaymentResponse getPaymentStatus(CoraConfig coraConfig, Payment payment, String authToken) {
        log.info("Fetching Payment Status...");

        var headers = getHeaders(authToken);
        var httpEntity = new HttpEntity<>(headers);

        var client = this.getClient(coraConfig);
        var response = client.exchange(HANDLE_PAYMENT_ENDPOINT, HttpMethod.GET, httpEntity, CoraPaymentResponse.class, payment.getTransactionId());

        return response.getBody();
    }

    public CoraPaymentResponse createPayment(CoraConfig coraConfig, CoraPaymentRequest paymentRequest, String requestId, String authToken) {
        log.info("Creating payment...");

        var headers = getHeaders(authToken);
        headers.add(IDEMPOTENCY_KEY_HEADER, requestId);

        var httpEntity = new HttpEntity<>(paymentRequest, headers);

        var client = this.getClient(coraConfig);
        var response = client.postForEntity(CREATE_PAYMENT_ENDPOINT, httpEntity, CoraPaymentResponse.class);

        return response.getBody();
    }

    public void cancelPayment(CoraConfig coraConfig, Payment payment, String authToken) {
        log.info("Cancelling payment...");

        var headers = getHeaders(authToken);
        var httpEntity = new HttpEntity<>(headers);

        var client = this.getClient(coraConfig);
        client.exchange(HANDLE_PAYMENT_ENDPOINT, HttpMethod.DELETE, httpEntity, CoraPaymentResponse.class, payment.getTransactionId());
    }

    private HttpHeaders getHeaders(String authToken) {
        var headers = new HttpHeaders();
        headers.setBearerAuth(authToken);

        return headers;
    }

    private RestTemplate getClient(CoraConfig coraConfig) {
        if (threadLocalClient.get() == null) {
            threadLocalClient.set(CoraHttpClient.getClient(coraConfig));
        }

        return threadLocalClient.get();
    }

}
