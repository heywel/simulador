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
@Table(name = "vende")
@Builder
public class Vende {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_venta", unique = true, nullable = false)
    private Integer codVenta;

    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "tiempo_atencion")
    private Integer tiempoAtencion;
}
