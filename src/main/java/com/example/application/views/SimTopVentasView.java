package com.example.application.views;

import com.example.application.services.model.Producto;
import com.example.application.services.model.TopVenta;
import com.example.application.services.model.Vende;
import com.example.application.services.service.ProductoService;
import com.example.application.services.service.TopVentaService;
import com.example.application.services.service.VendeService;
import com.example.application.services.utilApp.ConstantApp;
import com.example.application.views.extraComponents.builders.ButtonGen;
import com.example.application.views.extraComponents.builders.ComboBoxGen;
import com.example.application.views.extraComponents.builders.HorizontalLayoutGen;
import com.example.application.views.extraComponents.builders.TextFieldGen;
import com.example.application.views.utilViews.CreateComponent;
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.claspina.confirmdialog.ButtonOption;
import org.claspina.confirmdialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Route(value = "topventa", layout = MainView.class)
@PageTitle("Top ventas")
public class SimTopVentasView extends FormView{
    private Grid<Vende> grid = new Grid<>();
    private ListDataProvider<Vende> dataProvider;

    private ComboBox<Producto> idProducto;
    private DatePicker fechaVenta;
    private NumberField cantidad;
    //private BigDecimalField cantidadVendida;

    private BeanValidationBinder<Vende> binder;
    private Vende topVenta;
    private VendeService topVentaService;
    private ProductoService productoService;
    private List<Producto> productoList;

    public SimTopVentasView(@Autowired VendeService topVentaservice, @Autowired ProductoService productoService){
        this.topVentaService = topVentaservice;
        this.productoService = productoService;
        createEditorLayout();
        addInitial();
        configureGrid();
        bindFields();
        actionButtons();
        splitLayout.getSecondaryComponent().setVisible(false);
    }

    public void configureGrid(){
        List<ValueProvider<Vende, String>> valueProvider = new ArrayList<>();
        valueProvider.add(obj -> nombreProducto((obj.getIdProducto())));
        valueProvider.add(obj -> obj.getFechaVenta().format(ConstantApp.FORMATO_FECHA));
        valueProvider.add(obj -> obj.getCantidad().toString());
        Iterator<ValueProvider<Vende, String>> iterator = valueProvider.iterator();
        Iterator<ValueProvider<Vende, String>> iterator2 = valueProvider.iterator();

        grid.addColumn(iterator.next()).setHeader("Producto").setAutoWidth(true).setKey("codigo");
        grid.addColumn(iterator.next()).setHeader("Fecha venta").setAutoWidth(true).setKey("fechaInicio");
        grid.addColumn(iterator.next()).setHeader("Cantidad").setAutoWidth(true).setKey("fechaFin");
        grid.addColumn(new ComponentRenderer<>(this::createOptionsGrid)).setAutoWidth(true).setId("options");

        HeaderRow filterRow = grid.appendHeaderRow();
        grid.getColumns().forEach((column) -> {
            if (iterator2.hasNext()){
                ValueProvider<Vende, String> value = iterator2.next();
                TextField field = new TextFieldGen.Builder()
                        .setValueChangeMode(ValueChangeMode.EAGER)
                        .addThemeVariants(TextFieldVariant.LUMO_SMALL)
                        .setSizeFull()
                        .setPlaceHolder("Filtrar")
                        .build();
                field.addValueChangeListener((event) -> { dataProvider.addFilter(
                        sis -> StringUtils.containsIgnoreCase(value.apply(sis), field.getValue()));
                });
                filterRow.getCell(column).setComponent(field);
                field.setSizeFull();
            }

            if (column.getId().isPresent() && column.getId().get().equals("options")){
                filterRow.getCell(column).setComponent(CreateComponent.createHorizontalWithCenteredComponents(
                        CreateComponent.downloadExcel("Descargar Excel",
                                CreateComponent.generateExcel(grid, "Productos"), true),
                        CreateComponent.downloadCSV("Descargar CSV",
                                CreateComponent.generateCSV(grid, "Productos"), true)
                ));
            }

            column.setResizable(true);
            column.setSortable(true);
            column.setTextAlign(ColumnTextAlign.CENTER);
            column.setAutoWidth(true);
        });
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue()!=null){
                Optional<Vende> productoFromBackend = topVentaService.getById(event.getValue().getCodVenta());
                if (productoFromBackend!=null){
                    populateForm(productoFromBackend.get());
                } else{
                    refreshGrid();
                }
            } else{
                clearForm();
            }
        });

        card.add(grid);
        refreshGrid();
    }

    private void createEditorLayout(){
        FormLayout formLayout = new FormLayout();

        idProducto = new ComboBoxGen.Builder<Producto>()
                .setLabel("Producto")
                .setClearButtonVisible(true)
                .setItems(productoList)
                .setItemLabelGenerator(n -> ((Producto) n).getNombreProducto())
                .build();
        fechaVenta = new DatePicker();
        fechaVenta.setLabel("Fecha venta");
        cantidad = new NumberField() ;
        cantidad.setLabel("Cantidad");

        Button search = new ButtonGen.Builder()
                .setText("Buscar")
                .setIcon(FontAwesome.Solid.SEARCH.create())
                .build();
        configureList();
        Component[] fields = new Component[]{idProducto, fechaVenta, cantidad};

        formLayout.add(fields);
        editorDiv.add(formLayout);
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void configureList(){
        productoList = new ArrayList<>();
        productoList.addAll(productoService.getAll());
        idProducto.setItems(productoList);
    }

    public String nombreProducto(int id){
        //Integer f = Integer.parseInt(id);
        Optional<Producto> pr = productoService.getById(id);
        return pr.get().getNombreProducto();
    }

    private void actionButtons(){
        cancel.addClickListener( e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.topVenta == null){
                    this.topVenta = new Vende();
                }

                binder.writeBean(this.topVenta);
                if (this.topVenta.getCodVenta()!=null){
                    topVentaService.modify(this.topVenta);
                } else{
                    topVentaService.modify(this.topVenta);
                }
                clearForm();
                refreshGrid();
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }
        });
    }

    private Component createOptionsGrid(Vende obj){
        return new HorizontalLayoutGen.Builder()
                .setPadding(false)
                .setMargin(false)
                .setSizeFull()
                .setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER)
                .add(
                        new ButtonGen.Builder()
                                .setToolTip("Borrar")
                                .setIcon(VaadinIcon.TRASH.create())
                                .addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR,ButtonVariant.LUMO_SMALL)
                                .setClickListener(() -> {
                                    ConfirmDialog
                                            .createQuestion()
                                            .withCaption("Borrar")
                                            .withMessage("¿Quiere borrar el registro?")
                                            .withOkButton(() -> {
                                                topVentaService.delete(obj.getCodVenta());
                                                clearForm();
                                                refreshGrid();
                                                ConfirmDialog
                                                        .createInfo()
                                                        .withCaption("Información")
                                                        .withMessage("El registro se ah borrado")
                                                        .open();
                                            }, ButtonOption.focus(), ButtonOption.caption("Si"))
                                            .withCancelButton(ButtonOption.caption("No"))
                                            .open();
                                })
                                .build()
                )
                .build();
    }

    private void refreshGrid(){
        grid.select(null);
        dataProvider = new ListDataProvider<>(topVentaService.getAll());
        grid.setDataProvider(dataProvider);
    }

    private void clearForm(){
        populateForm(null);
    }

    private void populateForm(Vende value){
        this.topVenta = value;
        binder.readBean(this.topVenta);
    }

    public void bindFields(){
        binder = new BeanValidationBinder<>(Vende.class);
        binder.forField(fechaVenta).bind(Vende::getFechaVenta, Vende::setFechaVenta);
       // binder.forField(fechaFin).bind(TopVenta::getFechaFin, TopVenta::setFech);
    }
}
