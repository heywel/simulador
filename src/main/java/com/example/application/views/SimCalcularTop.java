package com.example.application.views;

import com.example.application.services.dto.request.VentaDTORequest;
import com.example.application.services.model.Producto;
import com.example.application.services.model.TopVenta;
import com.example.application.services.service.ProductoService;
import com.example.application.services.service.TopVentaService;
import com.example.application.services.service.VendeService;
import com.example.application.views.extraComponents.builders.ButtonGen;
import com.example.application.views.extraComponents.builders.ComboBoxGen;
import com.example.application.views.extraComponents.builders.VerticalLayoutGen;
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "calculartop", layout = MainView.class)
@PageTitle("Calcular Top")
public class SimCalcularTop extends FormView{

    private ComboBox producto;
    private DatePicker fechaInicio;
    private  DatePicker fechaFin;
    private Button calcular;

    private TopVenta topVenta;
    private TopVentaService topVentaService;
    private ProductoService productoService;
    private VendeService vendeService;

    List<Producto> productoList;
    public SimCalcularTop(@Autowired TopVentaService topVentaService, @Autowired ProductoService productoService,
                          @Autowired VendeService vendeService){
        this.topVentaService = topVentaService;
        this.productoService = productoService;
        this.vendeService = vendeService;

        addInitial();

        configureFormGeneral();
        splitLayout.getSecondaryComponent().setVisible(false);
    }

    public void configureFormGeneral(){
        //card.getElement().getStyle().set("background-color", "orange");
        H3 title = new H3("Calcular top de productos vendidos");
        producto = new ComboBoxGen.Builder<Producto>()
                .setItems(productoList)
                .setItemLabelGenerator(obj -> ((Producto) obj).getNombreProducto())
                .setLabel("Producto")
                .build();
        fechaInicio = new DatePicker("Fecha inicio");
        fechaFin = new DatePicker("Fecha fin");
        calcular = new ButtonGen.Builder()
                .setText("Calcular")
                .setIcon(FontAwesome.Solid.COMPRESS_ARROWS_ALT.create())
                .addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL)
                .setClickListener(() -> {
                    System.out.println(
                            vendeService.findByDate(VentaDTORequest.builder()
                                    .producto(1)
                                    .fechaInicio(fechaInicio.getValue())
                                    .fechaFin(fechaFin.getValue())
                                    .build()));
                })
                .build();
        configureCombo();
        FormLayout form = new FormLayout();
        form.setSizeFull();

        form.add(producto, fechaInicio, fechaFin);
        VerticalLayout verticalLayout = new VerticalLayoutGen.Builder()
                .add(title,form, calcular)
                .setWidth("97%")
                .build();
        card.add(verticalLayout);
    }

    private void configureCombo(){
        productoList = new ArrayList<>();
        productoList.addAll(productoService.getAll());
        producto.setItems(productoList);
    }
}
