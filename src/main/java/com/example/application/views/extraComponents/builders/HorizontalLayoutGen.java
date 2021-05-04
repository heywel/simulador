package com.example.application.views.extraComponents.builders;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HorizontalLayoutGen extends HorizontalLayout {
    private HorizontalLayoutGen(Builder builder) {
        if(builder.id!=null){
            setId(builder.id);
        }
        if(builder.widthFull!=null && builder.widthFull){
            setWidthFull();
        }
        if(builder.heightFull!=null && builder.heightFull){
            setHeightFull();
        }
        if(builder.sizeFull!=null && builder.sizeFull){
            setSizeFull();
        }
        if(builder.spacing!=null ){
            setSpacing(builder.spacing);
        }
        if(builder.margin!=null ){
            setMargin(builder.margin);
        }
        if(builder.padding!=null){
            setPadding(builder.padding);
        }
        if(builder.justifyContentMode!=null){
            setJustifyContentMode(builder.justifyContentMode);
        }
        if(builder.add!=null){
            builder.add.forEach(a -> add(a));
        }
    }

    public static class Builder {
        private String id;
        private Boolean widthFull;
        private Boolean heightFull;
        private Boolean sizeFull;
        private Boolean spacing;
        private Boolean margin;
        private Boolean padding;
        private FlexComponent.JustifyContentMode justifyContentMode;
        private List<Component> add;

        public Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setWidthFull() {
            this.widthFull = true;
            return this;
        }

        public Builder setHeightFull() {
            this.heightFull = true;
            return this;
        }

        public Builder setSizeFull() {
            this.sizeFull = true;
            return this;
        }

        public Builder setSpacing(Boolean spacing) {
            this.spacing = spacing;
            return this;
        }
        public Builder setMargin(Boolean margin) {
            this.margin = margin;
            return this;
        }

        public Builder setPadding(Boolean padding) {
            this.padding = padding;
            return this;
        }

        public Builder setJustifyContentMode(JustifyContentMode justifyContentMode) {
            this.justifyContentMode = justifyContentMode;
            return this;
        }

        public Builder add(Component... c) {
            this.add = new ArrayList<>();
            this.add.addAll(Arrays.stream(c).collect(Collectors.toList()));
            return this;
        }

        public HorizontalLayoutGen build(){
            return new HorizontalLayoutGen(this);
        }
    }
}
