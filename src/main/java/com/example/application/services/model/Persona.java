package com.example.application.services.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_persona", unique = true, nullable = false)
    private Integer codPersona;

    @Column(name = "dpi")
    private Integer dpi;

    @Column(name = "nombre_persona")
    private String nombrePersona;

    @Column(name = "apellido_persona")
    private String apellidoPersona;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "fecha_contrato")
    private LocalDate fechaContrato;

    @Column(name = "sueldo")
    private Double sueldo;
}
