package com.kakz.tradeIn.service;

import com.kakz.tradeIn.model.ForgotPasswordToken;
import com.kakz.tradeIn.model.User;
import com.kakz.tradeIn.domain.VerificationType;
import com.kakz.tradeIn.repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Override
    public ForgotPasswordToken createToken(User user,
                                           String id,
                                           String otp,
                                           VerificationType verificationType,
                                           String sendTo) {

        ForgotPasswordToken token = new ForgotPasswordToken();
        token.setOtp(otp);
        token.setSendTo(sendTo);
        token.setVerificationType(verificationType);
        token.setUser(user);
        token.setId(id);
        return forgotPasswordRepository.save(token);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> token = forgotPasswordRepository.findById(id);
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userId) {
        return forgotPasswordRepository.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
        forgotPasswordRepository.delete(token);
    }
}
