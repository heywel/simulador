package com.example.application.views.extraComponents.builders;

import com.vaadin.flow.component.checkbox.Checkbox;
import lombok.Data;

@Data
public class CheckBoxGen extends Checkbox {
    private CheckBoxGen(Builder builder) {
        if(builder.label!=null){
            setLabel(builder.label);
        }
        if(builder.readOnly!=null){
            setReadOnly(builder.readOnly);
        }
        if(builder.disable!=null){
            setDisabled(builder.disable);
        }
        if(builder.sizeFull!=null && builder.sizeFull){
            setSizeFull();
        }
        if(builder.changeListener!=null){
            addValueChangeListener(checkboxBooleanComponentValueChangeEvent -> builder.changeListener.run());
        }
    }

    public static class Builder {
        private String label;
        private Boolean readOnly;
        private Boolean disable;
        private Boolean sizeFull;
        private Runnable changeListener;


        public Builder() {
        }

        public Builder setLabel(String label) {
            this.label = label;
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

        public Builder setSizeFull() {
            this.sizeFull = true;
            return this;
        }

        public Builder setChangeListener(Runnable changeListener) {
            this.changeListener = changeListener;
            return this;
        }

        public CheckBoxGen build(){
            return new CheckBoxGen(this);
        }
    }
}
