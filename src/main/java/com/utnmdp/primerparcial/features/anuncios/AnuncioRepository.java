package com.utnmdp.primerparcial.features.anuncios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnuncioRepository extends JpaRepository<AnuncioEntity, Long> {
    List<AnuncioEntity> findByCampanaId(Long campanaId);
    List<AnuncioEntity> findByAnuncianteId(Long anuncianteId);
}
