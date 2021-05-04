package com.example.application.views.extraComponents.menu.util.css;

public enum BorderRadiusGen {
    S("var(--lumo-border-radius-s)"), M("var(--lumo-border-radius-m)"), L(
            "var(--lumo-border-radius-l)"),

    _50("50%");

    private String value;

    BorderRadiusGen(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
