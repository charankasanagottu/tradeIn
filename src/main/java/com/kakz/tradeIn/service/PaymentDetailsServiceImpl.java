package com.kakz.tradeIn.service;

import com.kakz.tradeIn.model.PaymentDetails;
import com.kakz.tradeIn.model.User;
import com.kakz.tradeIn.repository.PaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService{
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolder,
                                            String ifsc,
                                            String bankName,
                                            User user) {
        PaymentDetails paymentDetails = new PaymentDetails();

        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setUser(user);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBank(bankName);
        paymentDetails.setAccountHolderName(accountHolder);

        return paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) throws Exception {
        PaymentDetails paymentDetails = paymentDetailsRepository.findByUserId(user.getId());
        if(paymentDetails == null){
            throw new Exception("User does not hold any Account details ");
        }
        return paymentDetails;
    }
}
