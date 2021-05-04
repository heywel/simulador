package com.example.application.views.context;

import com.example.application.views.extraComponents.builders.ButtonGen;
import com.example.application.views.extraComponents.builders.HorizontalLayoutGen;

import com.example.application.views.extraComponents.builders.TextFieldGen;
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.componentfactory.EnhancedDialog;
import com.vaadin.componentfactory.theme.EnhancedDialogVariant;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.TextField;

import java.awt.*;

public class ModeloMMS extends EnhancedDialog {

    double lambda, mu;
    private TextField usoSistemaS2;
    private TextField usoSistemaS3;
    private TextField usoSistemaS4;
    private TextField usoSistemaS5;

    public ModeloMMS(Double lambda, Double mu){
        this.lambda = lambda;
        this.mu = mu;
        setThemeVariants(EnhancedDialogVariant.SIZE_SMALL);
        setHeader("Modelo MMS");
        createEditorLayout();
        //add(createEditorLayout());
        setFooter(createFooterButtons());
    }
    
    private void createEditorLayout(){
        usoSistemaS2 = new TextFieldGen.Builder()
                .setLabel("Uso del sistema con 2 servidores")
                .build();
        usoSistemaS2.setWidthFull();
        usoSistemaS3 = new TextFieldGen.Builder()
                .setLabel("Uso del sistema con 3 servidores")
                .build();
        usoSistemaS3.setWidthFull();
        usoSistemaS4 = new TextFieldGen.Builder()
                .setLabel("Uso del sistema con 4 servidores")
                .build();
        usoSistemaS4.setWidthFull();
        usoSistemaS5 = new TextFieldGen.Builder()
                .setLabel("Uso del sistema con 5 servidores")
                .build();
        usoSistemaS5.setWidthFull();

        double s2 = lambda/(2 * mu);
        double s3 = lambda/(3 * mu);
        double s4 = lambda/(4 * mu);
        double s5 = lambda/(5 * mu);

        if (s2<1){
            double lqs2 = ( Math.pow((lambda/mu), 2) *(lambda*mu) *(s2-1) )/( Math.pow((2*mu - lambda), 2));
            //System.out.println("s2 Clientes en cola: " + lqs2);
        }
        if (s3<1){
            double lqs3 = ( Math.pow((lambda/mu), 3) *(lambda*mu) *(s3-1) )/(2 * (Math.pow((3*mu - lambda), 2)));
            //System.out.println("Clientes en cola: " + lqs3);
        }
        if (s4<1){
            double lqs4 = ( Math.pow((lambda/mu), 4) *(lambda*mu) *(s4-1) )/(6 * (Math.pow((4*mu - lambda), 2)));
            //System.out.println("Clientes en cola: " + lqs4);
        }
        if (s5<1){
            double lqs5 = ( Math.pow((lambda/mu), 5) *(lambda*mu) *(s5-1) )/(24 * (Math.pow((5*mu - lambda), 2)));
            //System.out.println("Clientes en cola: " + lqs5);
        }

        usoSistemaS2.setValue("Uso del sistema : " + redo(s2)*100 + " %");
        usoSistemaS3.setValue("Uso del sistema : " + redo(s3)*100 + " %");
        usoSistemaS4.setValue("Uso del sistema : " + redo(s4)*100 + " %");
        usoSistemaS5.setValue("Uso del sistema : " + redo(s5)*100 + " %");



        add(usoSistemaS2, usoSistemaS3, usoSistemaS4, usoSistemaS5);
    }

    public Double redo(double n){
        return Math.rint(n*10000)/10000;
    }

    private Component[] createFooterButtons(){
        Button cancel = new ButtonGen.Builder()
                .setText("Salir")
                .setIcon(VaadinIcon.EXIT.create())
                .addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL)
                .setToolTip("Salir")
                .setClickListener(() -> this.close())
                .build();

        return new Component[]{new HorizontalLayoutGen.Builder()
                .setPadding(false)
                .setMargin(false)
                .setSizeFull()
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END)
                .add(cancel)
                .build()};
    }
}
