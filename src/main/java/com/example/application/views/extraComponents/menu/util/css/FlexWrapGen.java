package com.example.application.views.extraComponents.menu.util.css;

public enum FlexWrapGen {
    NO_WRAP("nowrap"), WRAP("wrap"), WRAP_REVERSE("wrap-reverse");

    private String value;

    FlexWrapGen(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
