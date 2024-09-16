package com.kakz.tradeIn.service;


import com.kakz.tradeIn.model.User;
import com.kakz.tradeIn.domain.VerificationType;

public interface UserService {
    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserByUserId(Long id) throws Exception;

    public User enableTwoFactorAuthentication(VerificationType verificationType,
                                              String sendTo,User user);

    public User updatePassword(User user,
                               String newPassword);


}
