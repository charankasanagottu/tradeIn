package com.kakz.tradeIn.service;

import com.kakz.tradeIn.config.JwtProvider;
import com.kakz.tradeIn.model.TwoFactorAuth;
import com.kakz.tradeIn.model.User;
import com.kakz.tradeIn.domain.VerificationType;
import com.kakz.tradeIn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;


    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("Could not find User by email " + email);
        }
        return user;
    }

    @Override
    public User findUserByUserId(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new Exception("Could not find User by id " + id);
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType,
                                              String sendTo,User user) {

        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);

        user.setTwoFactorAuth(twoFactorAuth);

        userRepository.save(user);

        return user;
    }

    @Override
    public User updatePassword(User user,
                               String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
