package br.med.nextmed.cora.infrastructure.mappers;

import br.med.nextmed.common.mappers.PaymentRequestMapper;
import br.med.nextmed.common.models.Customer;
import br.med.nextmed.common.models.CustomerContact;
import br.med.nextmed.common.models.Payment;
import br.med.nextmed.cora.domain.enums.CoraDiscountType;
import br.med.nextmed.cora.domain.enums.CoraNotificationType;
import br.med.nextmed.cora.domain.enums.CoraPaymentMethod;
import br.med.nextmed.cora.domain.models.CoraChannel;
import br.med.nextmed.cora.domain.models.CoraCustomer;
import br.med.nextmed.cora.domain.models.CoraCustomerAddress;
import br.med.nextmed.cora.domain.models.CoraCustomerDocument;
import br.med.nextmed.cora.domain.models.CoraDiscount;
import br.med.nextmed.cora.domain.models.CoraNotification;
import br.med.nextmed.cora.domain.models.CoraPaymentTerms;
import br.med.nextmed.cora.domain.models.CoraService;
import br.med.nextmed.cora.domain.models.request.CoraPaymentRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class CoraPaymentRequestRequestMapper implements PaymentRequestMapper {

    @Override
    public Object fromPayment(Payment payment) {
        // Customer
        var customer = this.buildCustomer(payment.getCustomer());

        // Services
        var services = this.buildServices(payment);

        // Payment terms
        var paymentTerms = this.buildPaymentTerms(payment);

        // Notification - Email / SMS
        var notification = this.buildNotification(payment);

        // Payment forms
        var paymentForms = Arrays.asList(CoraPaymentMethod.BANK_SLIP, CoraPaymentMethod.PIX);

        return CoraPaymentRequest.builder()
            .customer(customer)
            .services(services)
            .paymentForms(paymentForms)
            .paymentTerms(paymentTerms)
            .notification(notification)
            .build();
    }

    private CoraCustomer buildCustomer(Customer customer) {
        var coraCustomerDocument = new CoraCustomerDocument();
        coraCustomerDocument.setIdentity(customer.getDocument());
        coraCustomerDocument.setType(customer.getDocumentType());

        var coraCustomer = new CoraCustomer();
        coraCustomer.setName(customer.getName());
        coraCustomer.setEmail(customer.getEmail());
        coraCustomer.setDocument(coraCustomerDocument);

        if (customer.getAddress() != null) {
            var coraCustomerAddress = buildCoraCustomerAddress(customer);
            coraCustomer.setAddress(coraCustomerAddress);
        }

        return coraCustomer;
    }

    private CoraCustomerAddress buildCoraCustomerAddress(Customer customer) {
        var customerAddress = customer.getAddress();

        var coraCustomerAddress = new CoraCustomerAddress();
        coraCustomerAddress.setStreet(customerAddress.getAddress());
        coraCustomerAddress.setDistrict(customerAddress.getAddress2());
        coraCustomerAddress.setComplement(customerAddress.getAddress2());
        coraCustomerAddress.setCity(customerAddress.getCity());
        coraCustomerAddress.setState(customerAddress.getState());
        coraCustomerAddress.setNumber(customerAddress.getNumber());
        coraCustomerAddress.setZipCode(customerAddress.getZipCode());
        coraCustomerAddress.setCountry(customerAddress.getCountry());

        return coraCustomerAddress;
    }

    private List<CoraService> buildServices(Payment payment) {
        var service = new CoraService();
        service.setName(payment.getName());
        service.setDescription(payment.getDescription());
        service.setAmount(this.integerAmount(payment.getTotal()));

        return Collections.singletonList(service);
    }

    private CoraPaymentTerms buildPaymentTerms(Payment payment) {
        var paymentTerms = new CoraPaymentTerms();
        paymentTerms.setDueDate(payment.getDueDate());

        // Fine

        // Interest

        // Discount
        var discount = payment.getDiscount();
        if (discount != null && !discount.isZero() && CoraDiscountType.isValidDiscountType(discount.getType().name())) {
            var coraDiscount = new CoraDiscount();
            coraDiscount.setType(CoraDiscountType.fromString(discount.getType().name()));
            coraDiscount.setValue(discount.getAmount().doubleValue());

            paymentTerms.setDiscount(coraDiscount);
        }

        return paymentTerms;
    }

    private CoraNotification buildNotification(Payment payment) {
        var customer = payment.getCustomer();

        var notification = new CoraNotification();
        notification.setName(payment.getName());

        List<CoraChannel> channels = new ArrayList<>();
        for (CustomerContact customerContact : customer.getContacts()) {
            var channel = new CoraChannel();
            channel.setChannel(customerContact.getContactType());
            channel.setContact(customerContact.getContact());
            channel.setRules(Arrays.asList(CoraNotificationType.NOTIFY_TWO_DAYS_BEFORE_DUE_DATE, CoraNotificationType.NOTIFY_WHEN_PAID));

            channels.add(channel);
        }

        notification.setChannels(channels);

        return notification;
    }

    private Integer integerAmount(BigDecimal amount) {
        return amount.movePointRight(2).intValueExact();
    }

}
