package com.example.application.views.utilViews.gridExport;

import com.vaadin.flow.component.grid.Grid;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class ExcelFileBuilder<T> extends FileBuilder<T> {
    private static final String EXCEL_FILE_EXTENSION = ".xls";

    private Workbook workbook;
    private Sheet sheet;
    private int rowNr;
    private int colNr;
    private Row row;
    private Cell cell;
    private CellStyle boldStyle;

    ExcelFileBuilder(Grid<T> grid){ super(grid);}

    @Override
    void buildCell(Object value) {
        if (value == null) {
            cell.setBlank();
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof Double ) {
            cell.setCellValue((Double) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else {
            if (value.toString().contains("http")) {
                Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
                href.setAddress(value.toString());
                cell.setHyperlink(href);
                cell.setCellValue("Link");
            } else{
                cell.setCellValue(value.toString());
            }
        }
    }

    @Override
    String getFileExtension() {
        return EXCEL_FILE_EXTENSION;
    }

    @Override
    void writeToFile() {
        try {
            workbook.write(new FileOutputStream(file));
        } catch (Exception e) {
        }
    }

    @Override
    void onNewCell() {
        cell = row.createCell(colNr);
        colNr++;
    }

    @Override
    void buildColumnHeaderCell(String header) {
        buildCell(header);
        cell.setCellStyle(getBoldStyle());
    }

    private CellStyle getBoldStyle() {
        if (boldStyle == null) {
            Font bold = workbook.createFont();
            bold.setBold(true);

            boldStyle = workbook.createCellStyle();
            boldStyle.setFont(bold);
        }
        return boldStyle;
    }

    @Override
    void buildFooter() {
        for (int i = 0; i < getNumberOfColumns(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    @Override
    protected void resetContent() {
        workbook = createWorkbook();
        sheet = workbook.createSheet();
        colNr = 0;
        rowNr = 0;
        row = null;
        cell = null;
        boldStyle = null;
    }

    protected Workbook createWorkbook() {
        return new HSSFWorkbook();
    }

}
