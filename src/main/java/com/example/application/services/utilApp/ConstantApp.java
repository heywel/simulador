package com.example.application.services.utilApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class ConstantApp {
    public static final String PATH_SERVICES = "/services";

    public static DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static DateTimeFormatter FORMATOHORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static DateFormat FORMAT_DATE_JOINED = new SimpleDateFormat("ddMMyyyy");
}
