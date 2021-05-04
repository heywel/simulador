package com.example.application.views;

import com.example.application.views.extraComponents.builders.ButtonGen;
import com.example.application.views.extraComponents.builders.HorizontalLayoutGen;
import com.example.application.views.extraComponents.builders.VerticalLayoutGen;
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.github.appreciated.card.Card;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import org.vaddon.CustomMediaQuery;
import org.vaddon.css.query.MediaQuery;
import org.vaddon.css.query.values.WidthAttributes;

@CssImport("./styles/views/view.css")
public class FormView extends Div {
    public Button cancel;
    public Button save;
    public HorizontalLayout buttonLayout;

    public Div editorLayoutDiv;
    public Div editorDiv;
    public SplitLayout splitLayout = new SplitLayout();
    public VerticalLayout wrapper = new VerticalLayout();
    public Card card = new Card();

    public FormView(){
        setClassName("view-form");
        setSizeFull();
        splitLayout.setSizeFull();
        initialiceButtons();
        createEditorLayut();
        createButtonLayout();
        createGridLayout();
    }

    private void createButtonLayout(){
        buttonLayout = new HorizontalLayoutGen.Builder()
                .setId("button-layout")
                .setWidthFull()
                .setSpacing(true)
                .add(save, cancel)
                .build();
        editorLayoutDiv.add(buttonLayout);
    }

    private void initialiceButtons(){
        cancel = new ButtonGen.Builder()
                .setText("Cancelar")
                .setIcon(VaadinIcon.EXIT.create())
                .addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL)
                .setToolTip("Cancelar")
                .build();
        save = new ButtonGen.Builder()
                .setText("Guardar")
                .setIcon(FontAwesome.Regular.SAVE.create())
                .addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL)
                .setToolTip("Guardar")
                .build();
    }

    private void createEditorLayut(){
        editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");
        editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);
        createButtonLayout();
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createGridLayout(){
        wrapper = new VerticalLayoutGen.Builder()
                .setId("grid-wrapper")
                .setWidthFull()
                .build();
        card.setSizeFull();
        wrapper.add(card);
        splitLayout.addToPrimary(wrapper);
        splitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
    }

    private void updateSplit(Boolean updateSplit){
        if (updateSplit){
            splitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        } else{
            splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        }
    }

    public void addInitial(){
        CustomMediaQuery customMediaMore560 = new CustomMediaQuery(this::updateSplit);
        customMediaMore560.setQuery(new MediaQuery(new WidthAttributes.MinWidth("560px")));
        add(splitLayout, customMediaMore560);
    }
}
