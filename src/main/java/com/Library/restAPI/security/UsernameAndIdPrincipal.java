package com.Library.restAPI.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class UsernameAndIdPrincipal {

    private String username;
    private Long userId;

    public UsernameAndIdPrincipal(String username, Long userId){
        this.username = username;
        this.userId = userId;
    }

    public String getUsername(){
        return username;
    }

    public Long getUserId(){
        return userId;
    }

    public static UsernameAndIdPrincipal getPrincipal(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UsernameAndIdPrincipal)){
            throw new RuntimeException("Wrong ctx");
        }
        return (UsernameAndIdPrincipal) principal;
    }

    public static Long getIdFromSecurityCtx(){
        return getPrincipal().getUserId();
    }

    public static String  getUsernameFromSecurityCtx(){
        return getPrincipal().getUsername();
    }
}
