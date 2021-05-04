package com.example.application.views;

import com.example.application.views.extraComponents.builders.HorizontalLayoutGen;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "about", layout = MainView.class)
@PageTitle("Acerca de")
@CssImport("./views/about/about-view.css")
public class SimAboutView extends FormView {

    public SimAboutView() {
        addInitial();
        configureForm();
        splitLayout.getSecondaryComponent().setVisible(false);
    }

    public void configureForm(){
        TextArea text = new TextArea();
        H3 h3Title = new H3("Problema de investigación");
        text.setValue(
                "Se desea investigar el comportamiento de los clientes que llegan a comprar los" +
                " productos de Doña Lucia, cuanto tiempo tardan en esperar para que se les atiendan," +
                " cuantas personas hacen cola para ser atendidos.");
        text.setReadOnly(true);
        text.setWidthFull();

        TextArea text2 = new TextArea();
        H3 h3Title2 = new H3("Descripción del problema");
        text2.setValue("En Panadería Doña Lucía somos una empresa productora de pan y pasteles por" +
                " lo que es importante la aplicación de las Buenas Prácticas de Manufactura que se" +
                " define como las herramientas básicas para la obtención de productos seguros para" +
                " el consumo humano, que se centraliza en la higiene y la forma de manipulación." +
                " La empresa como productora de pan y pasteles, cumple con ciertos requisitos que" +
                " estipula el Ministerio de Salud a través del Reglamento Técnico Centroamericano de" +
                " alimentos procesados, para dar así un producto de calidad con los mejores" +
                " estándares de higiene, en el cual esta Institución ejercerá acciones de supervisión," +
                " control de calidad.\n\n" +
                " Por lo tanto, Panificadora Doña Lucia S.A. actualmente por la demanda de los" +
                " clientes en la venta de los productos en la sucursal Central, no se tiene un control" +
                " exacto de la cantidad de productos vendidos ya que no se tiene un sistema como" +
                " tal para dicho control, por tal razón los productos a veces se agotan ya que no se" +
                " realiza una estimación exacta del consumo de estos mismos.");
        text2.setReadOnly(true);
        text2.setWidthFull();

        TextArea text3 = new TextArea();
        H3 h3Title3 = new H3("Desarrollado por");
        text3.setValue(
                "Emmanuel Amperez- 201741942\n\n" +
                        "Heywel Castellanos - 201343233\n\n" +
                        "Jonatan Chub - 201545903\n\n\n"
        );
        text3.setReadOnly(true);
        text3.setWidthFull();

        card.add(new HorizontalLayoutGen.Builder()
                .add(h3Title)
                .setWidthFull()
                .setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER)
                .setPadding(true)
                .build());
        card.add(text);
        card.add(new HorizontalLayoutGen.Builder()
                .add(h3Title2)
                .setWidthFull()
                .setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER)
                .setPadding(true)
                .build());
        card.add(text2);
        card.add(new HorizontalLayoutGen.Builder()
                .add(h3Title3)
                .setWidthFull()
                .setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER)
                .setPadding(true)
                .build());
        card.add(text3);
    }
}
