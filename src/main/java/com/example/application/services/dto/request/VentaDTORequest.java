package com.example.application.services.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaDTORequest {
    private Integer producto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
