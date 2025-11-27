package com.coppel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Polizas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poliza {

    @Id // Llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPolizas")
    private Integer idPoliza;

    @ManyToOne
    @JoinColumn(name = "EmpleadoGenero", referencedColumnName = "IdEmpleado", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "SKU", referencedColumnName = "SKU", nullable = false)
    private Inventario inventario;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "Fecha", nullable = false)
    private LocalDateTime fecha; // Mapea a DATETIME en SQL Server

}