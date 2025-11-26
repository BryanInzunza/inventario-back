package com.coppel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Empleado") 
@Data
@NoArgsConstructor 
@AllArgsConstructor 
public class Empleado {

    @Id 
    @Column(name = "IdEmpleado") 
    private Integer idEmpleado;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Apellido", nullable = false)
    private String apellido;

    @Column(name = "Puesto")
    private String puesto;
}