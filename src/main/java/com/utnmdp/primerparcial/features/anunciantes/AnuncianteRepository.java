package com.utnmdp.primerparcial.features.anunciantes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnuncianteRepository extends JpaRepository<AnuncianteEntity, Long> {
    boolean existsByCuit(String cuit);
}
