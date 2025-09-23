package com.psicovirtual.email.utils.enums;

public enum EmailContentEnum {
    LOGO("\\{\\{logo\\}\\}"),
    COMPANY("\\{\\{company\\}\\}"),
    ADDRESS("\\{\\{address\\}\\}"),
    YEAR("\\{\\{year\\}\\}"),
    NAME("\\{\\{name\\}\\}"),
    LOGIN("\\{\\{login_link\\}\\}"),
    VERIFICATION("\\{\\{verification_link\\}\\}"),
    RESET("\\{\\{reset_link\\}\\}");

    private final String id;

    EmailContentEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
