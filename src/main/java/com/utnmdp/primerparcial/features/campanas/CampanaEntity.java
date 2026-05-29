package com.utnmdp.primerparcial.features.campanas;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "campanas")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class CampanaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_campana",nullable = false)
    private String nombre;

    @Column(name = "target_audiencia",nullable = false)
    private String audiencia;

    @Column(name = "tipo_dispositivo",nullable = false)
    private String tipoDispositivo;

    @Column(name = "fecha_fin",nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "presupuesto_base",nullable = false)
    private Double presupuestoBase;
}
