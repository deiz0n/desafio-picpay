package org.deizon.authservice.domain;

public enum RoleEnum {
    ADMIN(0),
    USER(1),
    MERCHANT(2);

    private final int code;

    RoleEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

