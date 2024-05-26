package com.heymart.supermarket.service;

public interface AuthServiceClient {
    public boolean verifyUserAuthorization(String action, String token);
}