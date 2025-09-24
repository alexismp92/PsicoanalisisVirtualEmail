package com.psicovirtual.email.utils.enums;

public enum EmailContentEnum {
    LOGO("\\{\\{logo_url\\}\\}"),
    COMPANY("\\{\\{company\\}\\}"),
    ADDRESS("\\{\\{address\\}\\}"),
    YEAR("\\{\\{year\\}\\}"),
    NAME("\\{\\{name\\}\\}"),
    LOGIN("\\{\\{login_url\\}\\}"),
    PAYMENT("\\{\\{payment_url\\}\\}"),
    RESET("\\{\\{reset_url\\}\\}"),
    SUPPORT("\\{\\{support_url\\}\\}");

    private final String id;

    EmailContentEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
