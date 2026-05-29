package com.utnmdp.primerparcial.features.anunciantes;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "anunciantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnuncianteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_empresa", nullable = false)
    private String nombreEmpresa;

    @Column(name = "cuit", nullable = false, unique = true)
    private String cuit;

    @Column(name = "es_cuenta_vip", nullable = false)
    private Boolean esCuentaVip;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}
