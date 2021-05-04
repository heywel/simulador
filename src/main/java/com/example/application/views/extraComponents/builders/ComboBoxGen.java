package com.example.application.views.extraComponents.builders;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import lombok.Data;

import java.util.Collection;

@Data
public class ComboBoxGen<T> extends ComboBox {
    private ComboBoxGen(Builder builder) {
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
        if(builder.itemLabelGenerator!=null){
            setItemLabelGenerator(builder.itemLabelGenerator);
        }
        if(builder.items!=null){
            setItems(builder.items);
        }
    }

    public static class Builder<T>{
        private String label;
        private Boolean clearButtonVisible;
        private Boolean readOnly;
        private Boolean disable;
        private ItemLabelGenerator<T> itemLabelGenerator;
        private Collection<T> items;

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

        public Builder setItemLabelGenerator(ItemLabelGenerator<T> itemLabelGenerator) {
            this.itemLabelGenerator = itemLabelGenerator;
            return this;
        }

        public Builder setItems(Collection<T> items) {
            this.items = items;
            return this;
        }

        public ComboBoxGen build(){
            return new ComboBoxGen(this);
        }
    }
}
