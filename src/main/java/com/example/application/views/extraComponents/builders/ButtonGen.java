package com.example.application.views.extraComponents.builders;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import dev.mett.vaadin.tooltip.Tooltips;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ButtonGen extends Button {
    private ButtonGen(Builder builder) {
        if(builder.text!=null){
            setText(builder.text);
        }
        if(builder.disable!=null){
            setDisabled(builder.disable);
        }
        if(builder.variants!=null){
            builder.variants.forEach(a -> addThemeVariants(a));
        }
        if(builder.sizeFull!=null && builder.sizeFull){
            setSizeFull();
        }
        if(builder.icon!=null){
            setIcon(builder.icon);
        }
        if(builder.clickListener!=null){
            addClickListener(buttonClickEvent -> builder.clickListener.run());
        }
        if(builder.toolTip!=null){
            Tooltips.getCurrent().setTooltip(this, builder.toolTip);
        }
        setVisible(builder.visible==null?true:builder.visible);
    }

    public static class Builder {
        private String text;
        private Boolean disable;
        private List<ButtonVariant> variants;
        private Boolean sizeFull;
        private Component icon;
        private Runnable clickListener;
        private String toolTip;
        private Boolean visible;

        public Builder() {
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setDisable(Boolean disable) {
            this.disable = disable;
            return this;
        }

        public Builder addThemeVariants(ButtonVariant... variants){
            this.variants = new ArrayList<>();
            this.variants.addAll(Arrays.stream(variants).collect(Collectors.toList()));
            return this;
        }

        public Builder setSizeFull() {
            this.sizeFull = true;
            return this;
        }

        public Builder setIcon(Component icon) {
            this.icon = icon;
            return this;
        }

        public Builder setClickListener(Runnable clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public Builder setToolTip(String toolTip) {
            this.toolTip = toolTip;
            return this;
        }

        public Builder isVisible(Boolean visible) {
            this.visible = visible;
            return this;
        }


        public ButtonGen build(){
            return new ButtonGen(this);
        }
    }
}
