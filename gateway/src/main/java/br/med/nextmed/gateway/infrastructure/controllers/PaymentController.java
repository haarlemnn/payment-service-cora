package br.med.nextmed.gateway.infrastructure.controllers;

import br.med.nextmed.common.models.request.CancelPaymentRequest;
import br.med.nextmed.common.models.request.InitPaymentRequest;
import br.med.nextmed.common.models.request.StatusPaymentRequest;
import br.med.nextmed.common.models.response.PaymentResponse;
import br.med.nextmed.gateway.domain.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/status")
    public ResponseEntity<PaymentResponse> getPaymentStatus(@RequestBody @Valid StatusPaymentRequest statusPaymentRequest) {
        var paymentResponse = paymentService.statusPayment(statusPaymentRequest);
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping("/init")
    public ResponseEntity<PaymentResponse> initPayment(@RequestBody @Valid InitPaymentRequest initPaymentRequest) {
        var paymentResponse = paymentService.initPayment(initPaymentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponse);
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelPayment(@RequestBody @Valid CancelPaymentRequest cancelPaymentRequest) {
        paymentService.cancelPayment(cancelPaymentRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
