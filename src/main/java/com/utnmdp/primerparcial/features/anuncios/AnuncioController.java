package com.utnmdp.primerparcial.features.anuncios;

import com.utnmdp.primerparcial.features.anuncios.dtos.AnuncioRequestDTO;
import com.utnmdp.primerparcial.features.anuncios.dtos.AnuncioResponseDTO;
import com.utnmdp.primerparcial.features.anuncios.dtos.CancelacionDTO;
import com.utnmdp.primerparcial.features.anuncios.dtos.GastoRequestDTO;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anuncios")
@RequiredArgsConstructor
public class AnuncioController {
    private final AnuncioService anuncioService;

    @PostMapping
    public ResponseEntity<AnuncioResponseDTO> registrar(@Valid @RequestBody AnuncioRequestDTO dto) {
        AnuncioResponseDTO response = anuncioService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/campana/{idCampana}")
    public ResponseEntity<List<AnuncioResponseDTO>> listarPorCampana(@PathVariable Long idCampana) {
        List<AnuncioResponseDTO> response = anuncioService.listarPorCampana(idCampana);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/gastos")
    public ResponseEntity<AnuncioResponseDTO> cargarGasto(
            @PathVariable Long id,
            @Valid @RequestBody GastoRequestDTO dto) {
        AnuncioResponseDTO response = anuncioService.cargarGastos(id, dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<CancelacionDTO> cancelar(@PathVariable Long id) {
        CancelacionDTO response = anuncioService.cancelar(id);
        return ResponseEntity.ok(response);
    }
}
