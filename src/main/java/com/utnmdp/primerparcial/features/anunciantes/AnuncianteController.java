package com.utnmdp.primerparcial.features.anunciantes;

import com.utnmdp.primerparcial.features.anunciantes.dtos.AnuncianteRequestDTO;
import com.utnmdp.primerparcial.features.anunciantes.dtos.AnuncianteResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anunciantes")
@RequiredArgsConstructor
public class AnuncianteController {
    private final AnuncianteService anuncianteService;

    @PostMapping
    public ResponseEntity<AnuncianteResponseDTO> crear(@Valid @RequestBody AnuncianteRequestDTO dto) {
        AnuncianteResponseDTO response = anuncianteService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AnuncianteResponseDTO> darDeBaja(@PathVariable Long id) {
        AnuncianteResponseDTO response = anuncianteService.darDeBaja(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AnuncianteResponseDTO>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String cuit) {
        List<AnuncianteResponseDTO> response = anuncianteService.listar(nombre, cuit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/campana/{idCampana}")
    public ResponseEntity<List<AnuncianteResponseDTO>> listarPorCampana(@PathVariable Long idCampana) {
        List<AnuncianteResponseDTO> response = anuncianteService.listarPorCampana(idCampana);
        return ResponseEntity.ok(response);
    }
}
