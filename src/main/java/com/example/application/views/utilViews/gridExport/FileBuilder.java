package com.example.application.views.utilViews.gridExport;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.binder.BeanPropertySet;
import com.vaadin.flow.data.binder.PropertyDefinition;
import com.vaadin.flow.data.binder.PropertySet;
import com.vaadin.flow.data.provider.DataCommunicator;
import com.vaadin.flow.data.provider.Query;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class FileBuilder<T> {
    private static final String TMP_FILE_NAME = "tmp";

    File file;
    private Grid<T> grid;
    private Collection<Grid.Column> columns;
    private PropertySet<T> propertySet;
    private Boolean headerRowBuilt = false;

    FileBuilder(Grid<T> grid) {
        this.grid = grid;
        columns = grid.getColumns().stream().filter(this::isExportable).collect(Collectors.toList());
        try {
            Field field = Grid.class.getDeclaredField("propertySet");
            field.setAccessible(true);
            Object propertySetRaw = field.get(grid);
            if (propertySetRaw != null) {
                propertySet = (PropertySet<T>) propertySetRaw;
            }
        } catch (Exception e) {
            throw new ExporterException("couldn't read propertyset information from grid", e);
        }
        if (columns.isEmpty()) {
            throw new ExporterException("No exportable column found, did you remember to set property name as the key for column");
        }
    }

    private boolean isExportable(Grid.Column column) {
        return column.isVisible() && column.getKey() != null && !column.getKey().isEmpty()
                && (propertySet == null || propertySet.getProperty(column.getKey()).isPresent());
    }

    InputStream build() {
        try {
            initTempFile();
            resetContent();
            buildFileContent();
            writeToFile();
            return new FileInputStream(file);
        } catch (Exception e) {
            throw new ExporterException("An error happened during exporting your Grid", e);
        }
    }

    private void initTempFile() throws IOException {
        if (file == null || file.delete()) {
            file = createTempFile();
        }
    }

    private void buildFileContent() {
        buildRows();
        buildFooter();
    }

    protected void resetContent() {

    }

    private void buildHeaderRow() {
        columns.forEach(column -> {
            Optional<PropertyDefinition<T, ?>> propertyDefinition = propertySet.getProperty(column.getKey());
            if (propertyDefinition.isPresent()) {
                onNewCell();
                buildColumnHeaderCell(column.getId().isPresent()?grid.getUI().get().getTranslation(column.getId().get()):column.getKey());
            } else {
            }
        });
        headerRowBuilt = true;
    }


    void buildColumnHeaderCell(String header) {

    }

    @SuppressWarnings("unchecked")
    private void buildRows() {
        Object filter = null;
        try {
            Method method = DataCommunicator.class.getDeclaredMethod("getFilter");
            method.setAccessible(true);
            filter = method.invoke(grid.getDataCommunicator());
        } catch (Exception e) {
        }

        Query streamQuery = new Query(0, grid.getDataProvider().size(new Query(filter)), grid.getDataCommunicator().getBackEndSorting(),
                grid.getDataCommunicator().getInMemorySorting(), null);
        Stream<T> dataStream = getDataStream(streamQuery);

        dataStream.forEach(t -> {
            onNewRow();
            buildRow(t);
        });
    }

    @SuppressWarnings("unchecked")
    private void buildRow(T item) {
        if (propertySet == null) {
            propertySet = (PropertySet<T>) BeanPropertySet.get(item.getClass());
            columns = columns.stream().filter(this::isExportable).collect(Collectors.toList());
        }
        if (!headerRowBuilt) {
            buildHeaderRow();
            onNewRow();
        }
        columns.forEach(column -> {
            Optional<PropertyDefinition<T, ?>> propertyDefinition = propertySet.getProperty(column.getKey());
            if (propertyDefinition.isPresent()) {
                onNewCell();
                buildCell(propertyDefinition.get().getGetter().apply(item));
            } else {
                throw new ExporterException("Column key: " + column.getKey() + " is a property which cannot be found");
            }
        });
    }

    void onNewRow() {

    }

    void onNewCell() {

    }

    abstract void buildCell(Object value);

    void buildFooter() {

    }

    abstract String getFileExtension();

    private File createTempFile() throws IOException {
        return File.createTempFile(TMP_FILE_NAME, getFileExtension());
    }

    abstract void writeToFile();

    int getNumberOfColumns() {
        return columns.size();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Stream<T> getDataStream(Query newQuery) {
        Stream<T> stream = grid.getDataProvider().fetch(newQuery);
        if (stream.isParallel()) {
            stream = stream.collect(Collectors.toList()).stream();
            assert !stream.isParallel();
        }
        return stream;
    }
}
