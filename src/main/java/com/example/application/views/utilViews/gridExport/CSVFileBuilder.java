package com.example.application.views.utilViews.gridExport;

import com.vaadin.flow.component.grid.Grid;

public class CSVFileBuilder<T> extends FileBuilder<T> {

    CSVFileBuilder(Grid<T> grid){
        super(grid);
    }
    @Override
    void buildCell(Object value) {

    }

    @Override
    String getFileExtension() {
        return null;
    }

    @Override
    void writeToFile() {

    }
}
