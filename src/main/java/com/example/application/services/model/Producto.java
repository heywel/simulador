package com.example.application.services.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto", unique = true, nullable = false)
    private Integer idProducto;

    @Column(name = "cod_producto")
    private String codProducto;

    @Column(name = "nombre_producto")
    private String nombreProducto;

    @Column(name = "medida")
    private String medida;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Column(name = "precio_mayorista")
    private Double precioMayorista;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "categoria")
    private String categoria;
}
