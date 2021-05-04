package com.example.application.views.extraComponents.menu.util.css;

public enum FlexDirectionGen {
    COLUMN("column"), COLUMN_REVERSE("column-reverse"), ROW("row"), ROW_REVERSE(
            "row-reverse");

    private String value;

    FlexDirectionGen(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
