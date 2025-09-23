package com.psicovirtual.email.utils.enums;

public enum EmailTypeEnum {
    WELCOME_USER("WELCOME_USER"),
    WELCOME_THERAPIST("WELCOME_THERAPIST"),
    APPROVED_THERAPIST("APPROVED_THERAPIST"),
    REJECTED_THERAPIST("REJECTED_THERAPIST"),
    TOKEN_RESET("TOKEN_RESET"),
    PASSWORD_UPDATE("PASSWORD_UPDATE"),
    PENDING_ADMIN("PENDING_ADMIN");

    private final String id;

    EmailTypeEnum(String id) {
        this.id = id;
    }

    public static EmailTypeEnum fromId(String id) {
        for (EmailTypeEnum type : values()) {
            if (type.id.equalsIgnoreCase(id)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with id " + id);
    }


}

