package com.psicovirtual.email.utils.enums;

public enum LanguageEnum {
    ENGLISH("ENGLISH"),
    SPANISH( "ESPANOL");

    private final String id;

    LanguageEnum(String id) {
        this.id = id;
    }

    public static LanguageEnum fromId(String id) {
        for (LanguageEnum lang : values()) {
            if (lang.id.equalsIgnoreCase(id)) {
                return lang;
            }
        }
        throw new IllegalArgumentException("No enum constant with id " + id);
    }

}
