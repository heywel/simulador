package com.example.application.views;

import com.example.application.services.utilApp.Charts;
import com.example.application.views.extraComponents.builders.HorizontalLayoutGen;
import com.example.application.views.extraComponents.menu.components.FlexBoxLayout;
import com.example.application.views.extraComponents.menu.frameDesign.ViewFrame;
import com.example.application.views.extraComponents.menu.size.Bottom;
import com.example.application.views.extraComponents.menu.size.Horizontal;
import com.example.application.views.extraComponents.menu.size.Right;
import com.example.application.views.extraComponents.menu.size.Top;
import com.example.application.views.extraComponents.menu.util.IconSize;
import com.example.application.views.extraComponents.menu.util.LumoStyles;
import com.example.application.views.extraComponents.menu.util.TextColor;
import com.example.application.views.extraComponents.menu.util.UIUtils;
import com.example.application.views.extraComponents.menu.util.css.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "hello", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Simulador")
@CssImport("./views/helloworld/hello-world-view.css")
public class HelloWorldView extends FormView {

    public HelloWorldView() {
        addInitial();
        createForm();
        splitLayout.getSecondaryComponent().setVisible(false);
    }

    public void createForm(){

        H2 h2Title = new H2("Simulador de llegadas y atención de clientes");

        TextArea text = new TextArea();
        text.setValue("Misión:\n" +
                "Ofrecemos a nuestros clientes productos de panadería y pastelería de alta calidad," +
                " utilizando las mejores materias primas, apoyándonos en personal y maquinaria " +
                "especializada.\n\n" +
                "Visión:\n" +
                "Ser altamente reconocidos como la mejor opción para satisfacer su gusto y " +
                "paladar, a través de producto diferenciado de panadería y pastelería de alta calidad, " +
                "a un precio razonable. \n\n" +
                "Ubicación: \n" +
                "Actualmente Panadería Doña Lucia cuenta con varias sucursales en los" +
                " departamentos de Alta y Baja Verapaz, pero la sucursal principal se encuentra en" +
                "2da. Calle 11-25 Zona 3, Barrio Asunción, Tactic Alta Verapaz.");
        text.setWidthFull();
        text.setReadOnly(true);

        Image image1 = new Image("images/fel.jpg", "felicidad");
        image1.setWidth("300px");
        image1.setHeight("300px");

        Image image2 = new Image("images/postre.jpg", "postre");
        image2.setWidth("300px");
        image2.setHeight("300px");

        Image image3 = new Image("images/sabor.jpg", "postre");
        image3.setWidth("300px");
        image3.setHeight("300px");

        Image image4 = new Image("images/tradicion.jpg", "icon");
        image4.setWidth("300px");
        image4.setHeight("300px");

        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.add(image1, image2, image3, image4);

        card.add(new HorizontalLayoutGen.Builder()
                .add(h2Title)
                .setWidthFull()
                .setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER)
                .setPadding(true)
                .build());
        card.add(text, hlayout);
    }


}
