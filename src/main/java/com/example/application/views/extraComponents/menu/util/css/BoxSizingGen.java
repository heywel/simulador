package com.example.application.views.extraComponents.menu.util.css;

public enum BoxSizingGen {
    BORDER_BOX("border-box"), CONTENT_BOX("content-box");

    private String value;

    BoxSizingGen(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
