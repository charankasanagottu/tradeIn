package com.kakz.tradeIn.controller;

import com.kakz.tradeIn.domain.PaymentMethod;
import com.kakz.tradeIn.model.PaymentOrder;
import com.kakz.tradeIn.model.User;
import com.kakz.tradeIn.response.PaymentResponse;
import com.kakz.tradeIn.service.PaymentService;
import com.kakz.tradeIn.service.UserService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order= paymentService.createPaymentOrder(user, amount,paymentMethod);

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse=paymentService.createRazorpayPaymentLink(user,amount, order.getId());
        }
        else{
            paymentResponse=paymentService.createStripPaymentLink(user,amount, order.getId());
        }

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }


}
