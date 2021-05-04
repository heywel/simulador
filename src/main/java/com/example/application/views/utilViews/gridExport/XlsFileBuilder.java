package com.example.application.views.utilViews.gridExport;

import com.vaadin.flow.component.grid.Grid;

public class XlsFileBuilder<T> extends ExcelFileBuilder<T>{
    XlsFileBuilder(Grid<T> grid){ super(grid);}
}
