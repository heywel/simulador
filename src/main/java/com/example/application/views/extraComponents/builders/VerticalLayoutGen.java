package com.example.application.views.extraComponents.builders;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class VerticalLayoutGen extends VerticalLayout {
    private VerticalLayoutGen(Builder builder) {
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
        if(builder.width!=null){
            setWidth(builder.width);
        }
        if(builder.height!=null){
            setHeight(builder.height);
        }
        if(builder.extraStyles!=null){
            Integer d = builder.extraStyles.length;
            for (int i=0;i<d;i++){
                getElement().getStyle().set(builder.extraStyles[i][0],builder.extraStyles[i][1]);
            }
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
        private String width;
        private String height;
        private String[][] extraStyles;
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

        public Builder setSizeFull(Boolean sizeFull) {
            this.sizeFull = sizeFull;
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

        public Builder setWidth(String width) {
            this.width = width;
            return this;
        }

        public Builder hasScroll() {
            this.extraStyles = new String[2][2];
            this.extraStyles[0][0] = "display";
            this.extraStyles[0][1] = "block";
            this.extraStyles[1][0] = "overflow";
            this.extraStyles[1][1] = "auto";
            return this;
        }

        public Builder setHeight(String height) {
            this.height = height;
            return this;
        }

        public Builder add(Component... c) {
            this.add = new ArrayList<>();
            this.add.addAll(Arrays.stream(c).collect(Collectors.toList()));
            return this;
        }

        public VerticalLayoutGen build(){
            return new VerticalLayoutGen(this);
        }
    }
}
