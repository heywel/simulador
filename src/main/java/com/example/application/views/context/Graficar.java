package com.example.application.views.context;

import com.example.application.services.utilApp.Charts;
import com.example.application.views.extraComponents.builders.ButtonGen;
import com.example.application.views.extraComponents.builders.HorizontalLayoutGen;
import com.example.application.views.extraComponents.menu.components.FlexBoxLayout;
import com.vaadin.componentfactory.EnhancedDialog;
import com.vaadin.componentfactory.theme.EnhancedDialogVariant;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class Graficar extends EnhancedDialog {

    public Graficar(double[] llegada, double[] servicio){
        setThemeVariants(EnhancedDialogVariant.SIZE_SMALL);
        setHeader("Graficas");
        crearGrafica(llegada, servicio);
        setFooter(createFooterButtons());
    }

    public void crearGrafica(double[] valores, double[] servicio){
        FlexBoxLayout lienzo = new FlexBoxLayout();
        Row charts = new Row();
        //
        charts.add(Charts.barChart(valores));

        //
        //charts.add(Charts.barChart(servicio));
        charts.add(Charts.lineChart(servicio));
        charts.setWidth("80%");
        charts.setHeight("80%");
        lienzo.add(charts);

        add(lienzo);
    }

    public Component[] createFooterButtons(){
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
