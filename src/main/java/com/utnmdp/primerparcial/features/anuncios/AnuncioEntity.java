package com.utnmdp.primerparcial.features.anuncios;

import com.utnmdp.primerparcial.features.anunciantes.AnuncianteEntity;
import com.utnmdp.primerparcial.features.campanas.CampanaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "anuncios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnuncioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_anuncio", nullable = false, unique = true, updatable = false)
    private String codigoAnuncio;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "inversion_estimada", nullable = false)
    private Double inversionEstimada;

    @Column(name = "inversion_total", nullable = false)
    private Double inversionTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoAnuncio estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_campana", nullable = false)
    private CampanaEntity campana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anunciante", nullable = false)
    private AnuncianteEntity anunciante;

    // se podria crear un tmb @PrePersist, en mi caso manejo esta asignacion en el service
    // recordar que el prePersist solo se asignan al momento de hacer el repo.save(entity)
    //@PrePersist
    //    public void prePersist() {
    //        if (this.codigoAnuncio == null) {
    //            this.codigoAnuncio = UUID.randomUUID().toString();
    //        }
    //        if (this.fechaCreacion == null) {
    //            this.fechaCreacion = LocalDateTime.now();
    //        }
    //        if (this.inversionTotal == null) {
    //            this.inversionTotal = 0.0;
    //        }
    //        if (this.estado == null) {
    //            this.estado = EstadoAnuncio.PENDIENTE;
    //        }
    //    }
}
