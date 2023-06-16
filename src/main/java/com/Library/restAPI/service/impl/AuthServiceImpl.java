package com.Library.restAPI.service.impl;

import com.Library.restAPI.dto.LoginRequest;
import com.Library.restAPI.dto.PasswordChangeRequest;
import com.Library.restAPI.dto.RegisterRequest;
import com.Library.restAPI.model.Token;
import com.Library.restAPI.model.User;
import com.Library.restAPI.repository.TokenRepository;
import com.Library.restAPI.repository.UserRepository;
import com.Library.restAPI.security.jwt.JwtService;
import com.Library.restAPI.service.AuthService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void login(LoginRequest loginRequest, HttpServletResponse response) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new UsernameNotFoundException(loginRequest.username()));

        UUID tokenId = UUID.randomUUID();   //Low probability of duplicate
        Cookie refreshCookie = jwtService.generateRefreshCookie(user, tokenId);
        saveUserToken(user, refreshCookie.getValue());

        response.addCookie(refreshCookie);
        response.addCookie(jwtService.generateAccessCookie(user,tokenId));

    }

    @Override
    public void register(RegisterRequest registerRequest, HttpServletResponse response) {
        User user = User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .email(registerRequest.email())
                .name(registerRequest.name())
                .surname(registerRequest.surname())
                .address(registerRequest.address())
                .city(registerRequest.city())
                .build();

        User savedUser = userRepository.save(user);
        UUID tokenId = UUID.randomUUID();   //Low probability of duplicate
        Cookie refreshCookie = jwtService.generateRefreshCookie(savedUser, tokenId);
        saveUserToken(savedUser, refreshCookie.getValue());

        response.addCookie(refreshCookie);
        response.addCookie(jwtService.generateAccessCookie(savedUser, tokenId));
    }


    @Override
    public void logoutAll(User user) {
        tokenRepository.deleteAll(user.getTokens());
    }

    @Override
    public void changePassword(String username, PasswordChangeRequest passwordChangeRequest,
                               HttpServletResponse response) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        boolean match = passwordEncoder.matches(passwordChangeRequest.oldPassword(), user.getPassword());

        if (!match)
            throw new InvalidParameterException("Wrong old password"); //TODO exception

        user.setPassword(passwordEncoder.encode(passwordChangeRequest.newPassword()));
        User savedUser = userRepository.save(user);
        logoutAll(savedUser);

        response.addCookie(jwtService.deleteAccessCookie());
        response.addCookie(jwtService.deleteRefreshToken());
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie[] cookies = request.getCookies();

        if (cookies == null){
            throw new InvalidParameterException("Refresh token not found"); //TODO exception
        }

        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(cookie1 -> cookie1.getName().equals("accessToken"))
                .findFirst()
                .orElse(null);


        if (cookie == null){
            throw new InvalidParameterException("Refresh token not found"); //TODO exception
        }

        String jwt = cookie.getValue();

        if (jwtService.isExpired(jwt)){ //TODO after adding trigger for auto delete expired token remove this if
            tokenRepository.deleteById(jwtService.extractId(jwt));
            response.addCookie(jwtService.deleteRefreshToken());
            throw new InvalidParameterException("Refresh token is expired"); //TODO exception
        }


        if (!tokenRepository.existsById(jwtService.extractId(jwt))){
            throw new InvalidParameterException("Wrong refresh token"); //TODO exception
        }

        response.addCookie(jwtService.generateAccessCookieFromToken(jwt));
    }

    private void saveUserToken(User user, String jwtToken){

        Token token = Token
                .builder()
                .user(user)
                .expireDate(jwtService.extractExpiration(jwtToken))
                .id(jwtService.extractId(jwtToken))
                .build();

        tokenRepository.save(token);
    }

}