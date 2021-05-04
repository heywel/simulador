package com.example.application.views.utilViews.gridExport;

public class ExporterException extends RuntimeException{
    ExporterException(String message){
        super(message);
    }

    ExporterException(String message, Exception e){
        super(message, e);
    }
}
