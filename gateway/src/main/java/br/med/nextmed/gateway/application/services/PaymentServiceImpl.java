package br.med.nextmed.gateway.application.services;

import br.med.nextmed.common.enums.ProviderType;
import br.med.nextmed.common.exceptions.CancelPaymentException;
import br.med.nextmed.common.exceptions.CreatePaymentException;
import br.med.nextmed.common.exceptions.DefaultRuntimeException;
import br.med.nextmed.common.exceptions.GetPaymentStatusException;
import br.med.nextmed.common.exceptions.MethodNotImplementedException;
import br.med.nextmed.common.models.request.CancelPaymentRequest;
import br.med.nextmed.common.models.request.InitPaymentRequest;
import br.med.nextmed.common.models.request.RefundPaymentRequest;
import br.med.nextmed.common.models.request.StatusPaymentRequest;
import br.med.nextmed.common.models.response.PaymentResponse;
import br.med.nextmed.common.utils.MDCUtils;
import br.med.nextmed.gateway.domain.factories.PaymentFactory;
import br.med.nextmed.gateway.domain.services.PaymentService;
import br.med.nextmed.gateway.infrastructure.factories.PaymentProviderFactory;
import br.med.nextmed.gateway.infrastructure.factories.RequestIdFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentProviderFactory paymentProviderFactory;

    private final RequestIdFactory requestIdFactory;

    @Autowired
    public PaymentServiceImpl(PaymentProviderFactory paymentProviderFactory, RequestIdFactory requestIdFactory) {
        this.paymentProviderFactory = paymentProviderFactory;
        this.requestIdFactory = requestIdFactory;
    }

    @Override
    public PaymentResponse initPayment(InitPaymentRequest request) {
        try {
            var requestId = requestIdFactory.generateUUID();
            var payment = PaymentFactory.fromInitPaymentRequest(request, requestId);
            MDCUtils.populateMDCWithPayment(payment);

            log.info("Init payment...");

            var paymentProvider = paymentProviderFactory.getPaymentProvider(ProviderType.CORA, request.getProviderSpecifics());

            var paymentResponse = paymentProvider.authorize(payment);
            log.info("Init payment...OK");

            return paymentResponse;
        } catch (DefaultRuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Init payment...FAILED", ex);
            throw new CreatePaymentException();
        }
    }

    @Override
    public PaymentResponse statusPayment(StatusPaymentRequest request) {
        try {
            var payment = PaymentFactory.fromStatusPaymentRequest(request);
            MDCUtils.populateMDCWithPayment(payment);

            log.info("Fetch payment status...");

            var paymentProvider = paymentProviderFactory.getPaymentProvider(ProviderType.CORA, request.getProviderSpecifics());

            var paymentResponse = paymentProvider.status(payment);
            log.info("Fetch payment status...OK");

            return paymentResponse;
        } catch (DefaultRuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Fetch payment status...FAILED", ex);
            throw new GetPaymentStatusException();
        }
    }

    @Override
    public void cancelPayment(CancelPaymentRequest request) {
        try {
            var payment = PaymentFactory.fromCancelPaymentRequest(request);
            MDCUtils.populateMDCWithPayment(payment);
            log.info("Cancel payment...");

            var paymentProvider = paymentProviderFactory.getPaymentProvider(ProviderType.CORA, request.getProviderSpecifics());

            paymentProvider.cancel(payment);
            log.info("Cancel payment...OK");
        } catch (DefaultRuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Cancel payment...FAILED", ex);
            throw new CancelPaymentException();
        }
    }

    @Override
    public PaymentResponse refundPayment(RefundPaymentRequest request) {
        throw new MethodNotImplementedException();
    }

}
