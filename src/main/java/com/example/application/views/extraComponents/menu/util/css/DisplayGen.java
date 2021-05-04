package com.example.application.views.extraComponents.menu.util.css;

public enum DisplayGen {
    BLOCK("block"), INLINE("inline"), FLEX("flex"), INLINE_FLEX("inline-flex");

    private String value;

    DisplayGen(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
