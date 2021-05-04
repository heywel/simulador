package com.example.application.views.extraComponents.builders;

import com.vaadin.flow.component.textfield.NumberField;
import lombok.Data;

@Data
public class NumberFieldGen extends NumberField {
    private NumberFieldGen(Builder builder) {
        if(builder.label!=null){
            setLabel(builder.label);
        }
        if(builder.max!=null){
            setMax(builder.max);
        }
        if(builder.min!=null){
            setMin(builder.min);
        }
        if(builder.step!=null){
            setStep(builder.step);
        }
        if(builder.clearButtonVisible!=null){
            setClearButtonVisible(builder.clearButtonVisible);
        }
        if(builder.hasControls!=null){
            setHasControls(builder.hasControls);
        }
        if(builder.readOnly!=null){
            setReadOnly(builder.readOnly);
        }
        if(builder.disable!=null){
            setDisabled(builder.disable);
        }
    }

    public static class Builder {
        private String label;
        private Double min;
        private Double max;
        private Double step;
        private Boolean clearButtonVisible;
        private Boolean hasControls;
        private Boolean readOnly;
        private Boolean disable;

        public Builder() {
        }

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setMin(Double min) {
            this.min = min;
            return this;
        }

        public Builder setMax(Double max) {
            this.max = max;
            return this;
        }

        public Builder setStep(Double step) {
            this.step = step;
            return this;
        }

        public Builder setClearButtonVisible(Boolean clearButtonVisible) {
            this.clearButtonVisible = clearButtonVisible;
            return this;
        }

        public Builder setHasControls(Boolean hasControls) {
            this.hasControls = hasControls;
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

        public NumberFieldGen build(){
            return new NumberFieldGen(this);
        }
    }
}
