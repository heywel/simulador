package com.example.application.views.utilViews;

import com.example.application.services.utilApp.ConstantApp;
import com.example.application.views.extraComponents.builders.ButtonGen;
import com.example.application.views.utilViews.gridExport.Exporter;
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import dev.mett.vaadin.tooltip.Tooltips;
import org.claspina.confirmdialog.ButtonOption;
import org.claspina.confirmdialog.ConfirmDialog;
import org.vaadin.olli.FileDownloadWrapper;

import java.util.Date;

public class CreateComponent {
    public static final HorizontalLayout createHorizontalWithCenteredComponents(Component... comp){
        HorizontalLayout h = new HorizontalLayout();
        h.setSizeFull();
        h.setPadding(false);
        h.setMargin(true);
        h.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        h.add(comp);
        return h;
    }

    public static final VerticalLayout createVerticalWithCenteredComponents(Component... comp){
        VerticalLayout v = new VerticalLayout();
        v.setSizeFull();
        v.setPadding(false);
        v.setMargin(true);
        v.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        v.add(comp);
        return v;
    }

    public static void cofirmDialog(String header, String description, String confirmText,
                                    Runnable confirmListener, String cancelText){
        ConfirmDialog confirmDialog = ConfirmDialog.create()
                .withCaption(header)
                .withMessage(description)
                .withCancelButton(ButtonOption.caption(cancelText))
                .withYesButton(confirmListener, ButtonOption.caption(confirmText));
        confirmDialog.open();
    }

    public static void createNotification(final String mensaje, final Notification.Position position,
                                          NotificationVariant type){
        Notification notification = new Notification(mensaje, 4000, position);
        notification.addThemeVariants(type);
        notification.open();
    }

    public static StreamResource generateExcel(Grid grid, String fileName){
        StreamResource resource = new StreamResource(fileName + "-" +
                ConstantApp.FORMAT_DATE_JOINED.format(new Date()) + ".xlsx",
                Exporter.exportAsExcel(grid));
        return resource;
    }

    public static StreamResource generateCSV(Grid grid, String fileName){
        final StreamResource resource = new StreamResource(fileName + "-" +
                ConstantApp.FORMAT_DATE_JOINED.format(new Date()) + ".csv",
                Exporter.exportAsCSV(grid));
        return resource;
    }

    public static Div downloadExcel(String title, StreamResource sr, Boolean visible){
        FileDownloadWrapper file = new FileDownloadWrapper(sr);
        FontAwesome.Solid.Icon icon = FontAwesome.Solid.FILE_EXCEL.create();
        Button b = new ButtonGen.Builder()
                .addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_SUCCESS)
                .setIcon(icon)
                .build();
        file.wrapComponent(b);
        Div d = new Div(file);
        Tooltips.getCurrent().setTooltip(d, title);
        d.setVisible(visible);
        return d;
    }

    public static Div downloadCSV(String title, StreamResource sr,Boolean visible){
        FileDownloadWrapper file = new FileDownloadWrapper(sr);
        FontAwesome.Solid.Icon icon = FontAwesome.Solid.FILE_CSV.create();
        Button b = new ButtonGen.Builder()
                .addThemeVariants(ButtonVariant.LUMO_SMALL)
                .setIcon(icon)
                .build();
        file.wrapComponent(b);
        Div d = new Div(file);
        Tooltips.getCurrent().setTooltip(d, title);
        d.setVisible(visible);
        return d;
    }
}
