package com.example.application.views.extraComponents.menu.size;

public interface Size {
    public String[] getMarginAttributes();

    public String[] getPaddingAttributes();

    // Spacing is applied via the class attribute
    public String getSpacingClassName();

    // Returns the size variable (Lumo custom property)
    public String getVariable();
}
