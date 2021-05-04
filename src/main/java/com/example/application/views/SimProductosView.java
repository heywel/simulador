package com.example.application.views;

import com.example.application.services.model.Producto;
import com.example.application.services.service.ProductoService;
import com.example.application.views.extraComponents.builders.ButtonGen;
import com.example.application.views.extraComponents.builders.HorizontalLayoutGen;
import com.example.application.views.extraComponents.builders.TextFieldGen;
import com.example.application.views.utilViews.CreateComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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

@Route(value = "producto", layout = MainView.class)
@PageTitle("Productos")
public class SimProductosView extends FormView {
    private Grid<Producto> grid = new Grid<>();
    private ListDataProvider<Producto> dataProvider;

    private TextField codProducto;
    private TextField nombreProducto;
    private TextField medida;
    private NumberField precioUnitario;
    private NumberField precioMayorista;
    private TextField categoria;

    private BeanValidationBinder<Producto> binder;
    private Producto producto;
    private ProductoService service;

    public SimProductosView(@Autowired ProductoService service){
        this.service = service;
        createEditorLayout();
        addInitial();
        configureGrid();
        bindFields();
        actionButtons();
    }

    public void configureGrid(){
        List<ValueProvider<Producto, String>> valueProvider = new ArrayList<>();
        valueProvider.add(Producto::getCodProducto);
        valueProvider.add(Producto::getNombreProducto);
        valueProvider.add(Producto::getMedida);
        valueProvider.add(obj -> obj.getPrecioUnitario().toString());
        valueProvider.add(obj -> obj.getPrecioMayorista().toString());
        valueProvider.add(Producto::getCategoria);
        Iterator<ValueProvider<Producto, String>> iterator = valueProvider.iterator();
        Iterator<ValueProvider<Producto, String>> iterator2 = valueProvider.iterator();

        grid.addColumn(iterator.next()).setHeader("Codigo").setAutoWidth(true).setKey("codigo");
        grid.addColumn(iterator.next()).setHeader("Nombre").setAutoWidth(true).setKey("Nombre");
        grid.addColumn(iterator.next()).setHeader("Medida").setAutoWidth(true).setKey("medida");
        grid.addColumn(iterator.next()).setHeader("Precio unitario").setAutoWidth(true).setKey("precioUnitario");
        grid.addColumn(iterator.next()).setHeader("Precio mayorista").setAutoWidth(true).setKey("precioMayorista");
        grid.addColumn(iterator.next()).setHeader("Categoria").setAutoWidth(true).setKey("categoria");
        grid.addColumn(new ComponentRenderer<>(this::createOptionsGrid)).setAutoWidth(true).setId("options");

        HeaderRow filterRow = grid.appendHeaderRow();
        grid.getColumns().forEach((column) -> {
            if (iterator2.hasNext()){
                ValueProvider<Producto, String> value = iterator2.next();
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
                Optional<Producto> productoFromBackend = service.getById(event.getValue().getIdProducto());
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
        codProducto = new TextFieldGen.Builder()
                .setLabel("Codigo")
                .build();
        medida = new TextFieldGen.Builder()
                .setLabel("Medida")
                .build();
        nombreProducto = new TextFieldGen.Builder()
                .setLabel("Descripción")
                .setClearButtonVisible(true)
                .build();
        precioUnitario = new NumberField();
        precioUnitario.setLabel("Precio");
        precioMayorista = new NumberField();
        precioMayorista.setLabel("Mayorista");
        categoria = new TextFieldGen.Builder()
                .setLabel("Categoria")
                .setClearButtonVisible(true)
                .build();

        Component[] fields = new Component[]{codProducto, nombreProducto, medida, precioUnitario, precioMayorista, categoria};

        formLayout.add(fields);
        editorDiv.add(formLayout);
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void actionButtons(){
        cancel.addClickListener( e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.producto == null){
                    this.producto = new Producto();
                }

                binder.writeBean(this.producto);
                if (this.producto.getIdProducto()!=null){
                    service.modify(this.producto);
                } else{
                    service.modify(this.producto);
                }
                clearForm();
                refreshGrid();
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }
        });
    }

    private Component createOptionsGrid(Producto obj){
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
                                                service.delete(obj.getIdProducto());
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
        dataProvider = new ListDataProvider<>(service.getAll());
        grid.setDataProvider(dataProvider);
        //grid.setDataProvider(dataProvider);
    }

    private void clearForm(){
        populateForm(null);
    }

    private void populateForm(Producto value){
        this.producto = value;
        binder.readBean(this.producto);
    }

    public void bindFields(){
        binder = new BeanValidationBinder<>(Producto.class);
        binder.bindInstanceFields(this);
    }
}
