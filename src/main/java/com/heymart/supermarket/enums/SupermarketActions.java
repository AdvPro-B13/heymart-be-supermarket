package com.heymart.supermarket.enums;
import lombok.Getter;

@Getter
public enum SupermarketActions {
    AUTH_CREATE_MANAGER("auth:create_manager");

    private final String value;

    private SupermarketActions(String value) {
        this.value = value;
    }

}
