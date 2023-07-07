package com.Library.restAPI.service.impl;

import com.Library.restAPI.dto.request.LoginRequest;
import com.Library.restAPI.dto.request.PasswordChangeRequest;
import com.Library.restAPI.dto.request.RegisterRequest;
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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(LoginRequest loginRequest, HttpServletResponse response) {

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

        return user;

    }

    @Override
    public User register(RegisterRequest registerRequest, HttpServletResponse response) {
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

        return savedUser;
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
    public User refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

        Token tokenFromDB = tokenRepository.findTokenById(jwtService.extractId(jwt))
                .orElseThrow(() -> new InvalidParameterException("Wrong refresh token")); //TODO exception

        response.addCookie(jwtService.generateAccessCookieFromToken(jwt));

        return tokenFromDB.getUser();
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
