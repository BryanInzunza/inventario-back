package com.coppel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Inventario")
@Data // Setter y getters
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @Column(name = "SKU")
    private String sku;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

}