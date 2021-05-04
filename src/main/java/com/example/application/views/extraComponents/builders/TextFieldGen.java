package com.example.application.views.extraComponents.builders;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TextFieldGen extends TextField {
    private TextFieldGen(Builder builder) {
        if(builder.label!=null){
            setLabel(builder.label);
        }
        if(builder.clearButtonVisible!=null){
            setClearButtonVisible(builder.clearButtonVisible);
        }
        if(builder.readOnly!=null){
            setReadOnly(builder.readOnly);
        }
        if(builder.disable!=null){
            setDisabled(builder.disable);
        }
        if(builder.variants!=null){
            builder.variants.forEach(a -> addThemeVariants(a));
        }
        if(builder.valueChangeMode!=null){
            setValueChangeMode(builder.valueChangeMode);
        }
        if(builder.sizeFull!=null && builder.sizeFull){
            setSizeFull();
        }
        if(builder.placeHolder!=null){
            setPlaceholder(builder.placeHolder);
        }
        if (builder.icon!=null){
            setPrefixComponent(builder.icon);
        }
    }

    public static class Builder {
        private String label;
        private Boolean clearButtonVisible;
        private Boolean readOnly;
        private Boolean disable;
        private List<TextFieldVariant> variants;
        private ValueChangeMode valueChangeMode;
        private Boolean sizeFull;
        private String placeHolder;
        private Icon icon;

        public Builder() {
        }

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setClearButtonVisible(Boolean clearButtonVisible) {
            this.clearButtonVisible = clearButtonVisible;
            return this;
        }

        public Builder setReadOnly(Boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder setDisable(Boolean disable) {
            this.disable = disable;
            return this;
        }

        public Builder addThemeVariants(TextFieldVariant... variants){
            this.variants = new ArrayList<>();
            this.variants.addAll(Arrays.stream(variants).collect(Collectors.toList()));
            return this;
        }

        public Builder setValueChangeMode(ValueChangeMode valueChangeMode) {
            this.valueChangeMode = valueChangeMode;
            return this;
        }

        public Builder setSizeFull() {
            this.sizeFull = true;
            return this;
        }

        public Builder setPlaceHolder(String placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder setIcon(Icon icon){
            this.icon = icon;
            return this;
        }

        public TextFieldGen build(){
            return new TextFieldGen(this);
        }
    }
}
