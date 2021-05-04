package com.example.application.views;

import com.example.application.services.model.Producto;
import com.example.application.services.model.TopVenta;
import com.example.application.services.model.Vende;
import com.example.application.services.service.ProductoService;
import com.example.application.services.service.TopVentaService;
import com.example.application.services.service.VendeService;
import com.example.application.views.context.Graficar;
import com.example.application.views.context.ModeloMMS;
import com.example.application.views.extraComponents.builders.*;
import com.example.application.views.utilViews.CreateComponent;
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "simular", layout = MainView.class)
@PageTitle("Simular")
public class SimCalculos extends FormView{

    Grid<Producto> grid = new Grid<>();
    ListDataProvider<Producto> dataProvider;

    private DatePicker fechaVenta;
    private TextField llegada;
    private TextField servicio;
    private TextField valLQ;
    private TextField valLS;
    private TextField valWq;
    private TextField valWS;
    private TextField usoSistema;
    private TextField sistemaDesocupado;
    private Button calcular;
    private Button guardar;
    private Button graficar;
    private Label lblTotalSum;
    private Checkbox check;
    private Double totalSum = 0.0;
    public double[] grLlegada;
    public double[] grServicio;

    private TopVenta topVenta;
    private TopVentaService topVentaService;
    private ProductoService productoService;
    private VendeService vendeService;

    private List<Vende> listVentas = new ArrayList<>();

    List<Producto> productoList;
    public SimCalculos(@Autowired TopVentaService topVentaService, @Autowired ProductoService productoService,
                       @Autowired VendeService vendeService){
        this.topVentaService = topVentaService;
        this.productoService = productoService;
        this.vendeService = vendeService;
        lblTotalSum = new Label("Total: Q " + totalSum);

        addInitial();
        configureFormGeneral();
        gridDetail();
        splitLayout.getSecondaryComponent().setVisible(false);
    }

    public void configureFormGeneral(){
        H3 title = new H3("Simular llegada de clientes por hora");
        fechaVenta = new DatePicker();
        fechaVenta.setLabel("Fecha");
        check = new CheckBoxGen.Builder()
                .setLabel("Ingresar datos manualmente")
                .setChangeListener(() -> {
                    check();
                })
                .build();
        llegada = new TextFieldGen.Builder()
                .setLabel("Tasa de llegada (lambda)")
                .setReadOnly(true)
                .build();
        servicio = new TextFieldGen.Builder()
                .setLabel("Tasa de servicio (mu)")
                .setReadOnly(true)
                .build();

        valLQ = new TextFieldGen.Builder()
                .setLabel("Clientes en cola (lq)")
                .setReadOnly(true)
                .build();
        valLS = new TextFieldGen.Builder()
                .setLabel("Clientes en el sistema (ls)")
                .setReadOnly(true)
                .build();
        valWq = new TextFieldGen.Builder()
                .setLabel("Tiempo en cola (wq)")
                .setReadOnly(true)
                .build();
        valWS = new TextFieldGen.Builder()
                .setLabel("Tiempo espera en el sistema (ws)")
                .setReadOnly(true)
                .build();
        usoSistema = new TextFieldGen.Builder()
                .setLabel("Utilización del sistemas (p)")
                .setReadOnly(true)
                .build();
        sistemaDesocupado = new TextFieldGen.Builder()
                .setLabel("Probabilidad de que el sistema este desocupado (q)")
                .setReadOnly(true)
                .build();
        calcular = new ButtonGen.Builder()
                .setText("Simular")
                .setIcon(FontAwesome.Solid.COMPRESS_ARROWS_ALT.create())
                .addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL)
                .setClickListener(() -> {
                    random();
                    totalSum =0.0;
                })
                .build();
        guardar = new ButtonGen.Builder()
                .setText("Guardar")
                .setIcon(FontAwesome.Solid.SAVE.create())
                .addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL)
                .setClickListener(() -> {
                    if (fechaVenta.getValue()==null){
                        CreateComponent.createNotification("Ingrese una fecha", Notification.Position.TOP_CENTER,
                                NotificationVariant.LUMO_ERROR);
                    }else{
                        int cant = listVentas.stream().map(a -> a.getCantidad()).max(Comparator.naturalOrder()).get();
                        List<Vende> aux = listVentas.stream().filter(a -> a.getCantidad().equals(cant)).collect(Collectors.toList());

                        System.out.println("List ventas "+listVentas);
                        System.out.println("Aux "+aux);

                        aux.forEach(a -> {
                            vendeService.create(Vende.builder()
                                    .fechaVenta(fechaVenta.getValue())
                                    .idProducto(a.getIdProducto())
                                    .cantidad(a.getCantidad())
                                    .build());
                        });
                        listVentas = new ArrayList<>();
                        CreateComponent.createNotification("Productos agregados en el top de ventas", Notification.Position.TOP_CENTER,
                                NotificationVariant.LUMO_SUCCESS);
                    }
                })
                .build();
        graficar = new ButtonGen.Builder()
                .setText("Graficar")
                .setIcon(VaadinIcon.CHART.create())
                .addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL)
                .setClickListener(() -> {
                    new Graficar(grLlegada, grServicio).open();
                })
                .build();

        HorizontalLayout hButtons = new HorizontalLayoutGen.Builder()
                .add(calcular, guardar, graficar)
                .build();

        HorizontalLayout hl = new HorizontalLayoutGen.Builder()
                .setSpacing(true)
                .setPadding(true)
                .add(fechaVenta, check)
                .build();
        FormLayout form = new FormLayout();
        form.setSizeFull();

        form.add(llegada, servicio, valLQ, valLS, valWq,valWS, usoSistema, sistemaDesocupado);
        VerticalLayout verticalLayout = new VerticalLayoutGen.Builder()
                .add(title, hl, form, hButtons)
                .setWidth("97%")
                .build();
        card.add(verticalLayout, grid);
    }

    public void check(){
        if(check.getValue()){
            llegada.setReadOnly(false);
            servicio.setReadOnly(false);
        }else{
            llegada.setReadOnly(true);
            servicio.setReadOnly(true);
        }
    }

    public void random(){

        double lambda=0, mu=0;

        if (check.getValue()){
            if ( llegada.isEmpty() ){
                llegada.setInvalid(true);
                llegada.setErrorMessage("Ingrese un dato");
            }else if( servicio.isEmpty() ){
                servicio.setInvalid(true);
                servicio.setErrorMessage("Ingrese un dato");
            } else{
               lambda = Double.parseDouble(llegada.getValue());
               mu = Double.parseDouble(servicio.getValue());

                double lq = (Math.pow(lambda, 2)) / (mu * (mu - lambda));
                double ls  = lambda / (mu - lambda);
                double wq = lq/lambda;
                double ws =  wq + (1/mu);
                double p = lambda/mu;
                double q = 1 - p;

                if (lambda>mu){
                    //CreateComponent.createNotification("Se necesitan mas empleados",
                            //Notification.Position.TOP_CENTER, NotificationVariant.LUMO_ERROR);
                    new ModeloMMS(lambda, mu).open();
                }

                valLQ.setValue(redo(lq) +"");
                valLS.setValue(redo(ls) +"");
                valWq.setValue(redo(wq) + "");
                valWS.setValue(redo(ws) + "");
                usoSistema.setValue( redo(p)*100 +" %" );
                sistemaDesocupado.setValue( redo(q)*100 + " %" );

                graficaLlegada(lambda);
                graficaServicio(mu);
                //refreshGrid();
            }
        }else{
            lambda =  Math.floor(Math.random() * 10 + 1);
            mu = Math.floor(Math.random() * 15 + 1);
            llegada.setValue(lambda + "");
            servicio.setValue(mu + "");

            double lq = (Math.pow(lambda, 2)) / (mu * (mu - lambda));
            double ls  = lambda / (mu - lambda);
            double wq = lq/lambda;
            double ws =  wq + (1/mu);
            double p = lambda/mu;
            double q = 1 - p;

            if (lambda>mu){
                //CreateComponent.createNotification("Se necesitan mas empleados",
                        //Notification.Position.TOP_CENTER, NotificationVariant.LUMO_ERROR);
                new ModeloMMS(lambda, mu).open();
            }

            valLQ.setValue(redo(lq) +"");
            valLS.setValue(redo(ls) +"");
            valWq.setValue(redo(wq) + "");
            valWS.setValue(redo(ws) + "");
            usoSistema.setValue( redo(p)*100 +" %" );
            sistemaDesocupado.setValue( redo(q)*100 + " %" );

            graficaLlegada(lambda);
            graficaServicio(mu);
            refreshGrid();
        }
    }

    public void graficaLlegada(double lamda){
        grLlegada = new double[6];
        for (int i=1; i<=6; i++){
            double p = (Math.pow(lamda, i) * Math.exp(-lamda))/(factorial(i));
            grLlegada[i-1]=p;
        }
    }

    public void graficaServicio(double mu){
        grServicio = new double[10];
        for (int i=1; i<=10; i++){
            double s = 1 - Math.exp(-mu * (i*10)/60);
            grServicio[i-1] = s;
        }
    }


    public Double redo(double n){
        return Math.rint(n*10000)/10000;
    }

    public Double factorial(int n){
        if (n==0){
            return 1.0;
        }else{
            return n * factorial(n-1);
        }
    }

    public void gridDetail(){
        grid.addColumn(Producto::getNombreProducto).setHeader("Nombre").setAutoWidth(true).setKey("Nombre");
        grid.addColumn(Producto::getPrecioUnitario).setHeader("Precio unitario").setAutoWidth(true).setKey("precioUnitario");
        grid.addColumn(obj -> precioXproducto(obj)).setFooter(lblTotalSum).setHeader("Cantidad y precios de productos vendidos");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setWidthFull();
    }

    private void refreshGrid(){
        grid.select(null);
        grid.setItems(obtainProducts());

    }

    private List<Producto> obtainProducts(){
        int r = (int)Math.floor(Math.random() * 15 + 1);
        List<Producto> listProd = new ArrayList<>();
        for (int i=0; i<r; i++){
            int id = (int)Math.floor(Math.random() * 25 + 1);
            Optional<Producto> producto =  productoService.getById(id);
            if (!producto.isEmpty()){
                listProd.add(producto.get());
            }
        }
        return listProd;
    }

    public String precioXproducto (Producto obj){
        String[] resolver = {null};
        int cantidad = (int)Math.floor(Math.random() * 15 + 1);
        resolver[0] = "Cantidad: " + cantidad + " | Total: " + obj.getPrecioUnitario()*cantidad;
        totalSum = totalSum + obj.getPrecioUnitario()*cantidad;
        lblTotalSum.setText( "Total: Q " + totalSum);
        listVentas.add(Vende.builder()
                .cantidad(cantidad)
                .idProducto(obj.getIdProducto())
                .fechaVenta(fechaVenta.getValue())
                .build());
        return resolver[0];
    }
}
