package com.Library.restAPI.security.jwt;

import com.Library.restAPI.repository.TokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {


        SecurityContextHolder.clearContext();
        Cookie[] cookies = request.getCookies();

        if (cookies == null){
            return;
        }

        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(cookie1 -> cookie1.getName().equals("accessToken"))
                .findFirst()
                .orElse(null);


        if (cookie == null){
            return;
        }

        String jwt = cookie.getValue();
        tokenRepository.deleteById(jwtService.extractId(jwt));

        response.addCookie(jwtService.deleteAccessCookie());
        response.addCookie(jwtService.deleteRefreshToken());
    }
}
