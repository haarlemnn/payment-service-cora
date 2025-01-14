package br.med.nextmed.cora.application;

import br.med.nextmed.common.contracts.PaymentProvider;
import br.med.nextmed.common.enums.ProviderType;
import br.med.nextmed.common.models.Payment;
import br.med.nextmed.common.models.response.PaymentResponse;
import br.med.nextmed.common.utils.MDCUtils;
import br.med.nextmed.cora.domain.models.CoraConfig;
import br.med.nextmed.cora.domain.models.request.CoraPaymentRequest;
import br.med.nextmed.cora.infrastructure.http.CoraAPIService;
import br.med.nextmed.cora.infrastructure.mappers.CoraPaymentRequestRequestMapper;
import br.med.nextmed.cora.infrastructure.mappers.CoraPaymentResponseMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CoraPaymentProvider implements PaymentProvider {

    private CoraConfig coraConfig;

    private final CoraAPIService coraAPIService;
    private final CoraPaymentRequestRequestMapper paymentRequestMapper;
    private final CoraPaymentResponseMapper paymentResponseMapper;

    @Autowired
    public CoraPaymentProvider(CoraAPIService coraAPIService, CoraPaymentRequestRequestMapper paymentRequestMapper, CoraPaymentResponseMapper paymentResponseMapper) {
        this.coraAPIService = coraAPIService;
        this.paymentRequestMapper = paymentRequestMapper;
        this.paymentResponseMapper = paymentResponseMapper;
    }

    @Override
    public PaymentResponse authorize(Payment payment) throws JsonProcessingException {
        var authResponse = this.coraAPIService.getAuthToken(coraConfig);
        var paymentRequest = this.paymentRequestMapper.fromPayment(payment);

        var response = this.coraAPIService.createPayment(coraConfig, (CoraPaymentRequest) paymentRequest, payment.getRequestId(), authResponse.getAccessToken());
        return this.paymentResponseMapper.toPaymentResponse(payment, response, payment.getRequestId());
    }

    @Override
    public void cancel(Payment payment) throws JsonProcessingException {
        var authResponse = this.coraAPIService.getAuthToken(coraConfig);
        this.coraAPIService.cancelPayment(coraConfig, payment, authResponse.getAccessToken());
    }

    @Override
    public PaymentResponse status(Payment payment) throws JsonProcessingException {
        var authResponse = this.coraAPIService.getAuthToken(coraConfig);
        var response = this.coraAPIService.getPaymentStatus(coraConfig, payment, authResponse.getAccessToken());
        return this.paymentResponseMapper.toPaymentResponse(payment, response, null);
    }

    @Override
    public ProviderType type() {
        return ProviderType.CORA;
    }

    @Override
    public void setProviderSpecifics(Map<String, String> providerSpecifics) {
        log.info("Setting CORA provider specifics...");
        this.coraConfig = CoraConfig.builder()
            .baseUrl(providerSpecifics.get("baseUrl"))
            .grantType(providerSpecifics.get("grantType"))
            .clientId(providerSpecifics.get("clientId"))
            .certificate(providerSpecifics.get("certificate"))
            .privateKey(providerSpecifics.get("privateKey"))
            .build();
    }

}
