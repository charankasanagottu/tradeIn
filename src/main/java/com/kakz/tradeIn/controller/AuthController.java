package com.kakz.tradeIn.controller;

import com.kakz.tradeIn.config.JwtProvider;
import com.kakz.tradeIn.model.TwoFactorOTP;
import com.kakz.tradeIn.model.User;
import com.kakz.tradeIn.repository.UserRepository;
import com.kakz.tradeIn.response.AuthResponse;
import com.kakz.tradeIn.service.CustomUserDetailsService;
import com.kakz.tradeIn.service.EmailService;
import com.kakz.tradeIn.service.TwoFactorOtpService;
import com.kakz.tradeIn.service.WatchlistService;
import com.kakz.tradeIn.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private WatchlistService watchlistService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExists = userRepository.findByEmail(user.getEmail());
        if(isEmailExists!=null){
            throw new Exception("Email Already Exists with another Account ");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());

        userRepository.save(newUser);

        watchlistService.createWatchList(newUser);

        Authentication authentication=new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("Registration successfully Done!!");


        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

        String email = user.getEmail();
        String password = user.getPassword();
        Authentication authentication= authenticate(email,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        User authUser = userRepository.findByEmail(email);

        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse authRes = new AuthResponse();
            authRes.setMessage("Two Factor Authorization is Enabled");
            authRes.setIsTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOTP();

            TwoFactorOTP oldTwoFactorOtp =
                    twoFactorOtpService.findByUser(authUser.getId());
            if(oldTwoFactorOtp!=null){
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);
            }

            TwoFactorOTP newTwoFactorOtp = twoFactorOtpService.
                    createTwoFactorOtp(
                    authUser,otp,jwt);


            emailService.sendVerificationOtpEmail(email,otp);

            authRes.setSession(newTwoFactorOtp.getId());
            return new ResponseEntity<>(authRes,HttpStatus.ACCEPTED);
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("Login successfully Done!!");

        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userdetails = customUserDetailsService.loadUserByUsername(email);
        if (userdetails == null) {
            throw new BadCredentialsException("" +
                    "Invalid email or password");
        }
        if (!password.equals(userdetails.getPassword())) {
            throw new BadCredentialsException("" +
                    "Invalid password");
        } else {
            return new UsernamePasswordAuthenticationToken(
                    userdetails,
                    password,
                    userdetails.getAuthorities());
        }
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOtp(
            @PathVariable String otp,
            @RequestParam String id) throws Exception {
        TwoFactorOTP twofactorotp = twoFactorOtpService.findById(id);
        if(twoFactorOtpService.verifyTwoFactorOtp(twofactorotp,otp)){
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Two Factor Authentication Verified");
            authResponse.setIsTwoFactorAuthEnabled(true);
            authResponse.setJwt(twofactorotp.getJwt());
            return new ResponseEntity<>(authResponse,HttpStatus.OK);
        }
        throw new Exception("Invalid otp");
    }
}
