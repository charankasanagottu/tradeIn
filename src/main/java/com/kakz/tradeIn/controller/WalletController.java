package com.kakz.tradeIn.controller;

import com.kakz.tradeIn.model.*;
import com.kakz.tradeIn.response.PaymentResponse;
import com.kakz.tradeIn.service.OrderService;
import com.kakz.tradeIn.service.PaymentService;
import com.kakz.tradeIn.service.UserService;
import com.kakz.tradeIn.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
//@RequestMapping("")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getuserWallet(
            @RequestHeader("Authorization") String authorization) throws Exception {
        User user  = userService.findUserProfileByJwt(authorization);
        Wallet wallet = walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("api/wallet/{receiverWalletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long receiverWalletId,
            @RequestBody WalletTransaction walletTransaction
            ) throws Exception {
        User senderUser = userService.findUserProfileByJwt(authorization);
        Wallet receiverWallet = walletService.findWalletById(receiverWalletId);
        Wallet wallet = walletService.walletToWalletTransfer(
                senderUser,
                receiverWallet,
                walletTransaction.getAmount());
        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long orderId
    ) throws Exception {

        User senderUser = userService.findUserProfileByJwt(authorization);
        Order order = orderService.getOrderById(orderId);
        Wallet wallet = walletService.payOrderPayment(order,senderUser);

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/deposit")  
    public ResponseEntity<Wallet> addMoneyToWallet(
            @RequestHeader("Authorization")String jwt,
            @RequestParam(name="order_id") Long orderId,
            @RequestParam(name="payment_id")String paymentId
    ) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);

        if(wallet.getBalance()==null) {
            wallet.setBalance(BigDecimal.ZERO);
        }
        PaymentOrder order = paymentService.getPaymentOrderById(orderId);
        Boolean status=paymentService.proceedPaymentOrder(order,paymentId);
        PaymentResponse res = new PaymentResponse();
        res.setPayment_url("deposit success");

        if(status){
            wallet=walletService.addBalance(wallet, order.getAmount());
        }

        return new ResponseEntity<>(wallet,HttpStatus.OK);

    }


}
