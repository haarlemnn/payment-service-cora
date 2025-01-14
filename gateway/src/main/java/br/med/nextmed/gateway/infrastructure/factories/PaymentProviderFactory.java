package br.med.nextmed.gateway.infrastructure.factories;

import br.med.nextmed.common.contracts.PaymentProvider;
import br.med.nextmed.common.enums.ProviderType;
import br.med.nextmed.gateway.domain.exceptions.PaymentProviderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PaymentProviderFactory {

    private final List<PaymentProvider> paymentProviderList;

    @Autowired
    public PaymentProviderFactory(List<PaymentProvider> paymentProviderList) {
        this.paymentProviderList = paymentProviderList;
    }

    public PaymentProvider getPaymentProvider(ProviderType providerType, Map<String, String> providerSpecifics) {
        log.info("PaymentProviderFactory [getPaymentProvider] - Getting payment provider with type {}", providerType);
        for (PaymentProvider paymentProvider : paymentProviderList) {
            if (paymentProvider.type().equals(providerType)) {
                paymentProvider.setProviderSpecifics(providerSpecifics);
                return paymentProvider;
            }
        }

        throw new PaymentProviderNotFoundException();
    }

}
