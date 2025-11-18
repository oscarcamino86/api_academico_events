package com.academico.events.controller;

import com.academico.events.model.Matricula;
import com.academico.events.service.MatriculaEventService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para manejar eventos de matrícula
 */
@RestController
@RequestMapping("/api/matricula")
@CrossOrigin(origins = "*")
public class MatriculaController {

    private static final Logger logger = LoggerFactory.getLogger(MatriculaController.class);

    @Autowired
    private MatriculaEventService matriculaEventService;

    @PostMapping("/events/create")
    public ResponseEntity<String> createMatriculaEvent(@Valid @RequestBody Matricula matricula) {
        logger.info("POST /api/matricula/events/create - Publicando evento de creación de matrícula");
        try {
            matriculaEventService.publishCreateEvent(matricula);
            logger.info("Evento de creación publicado exitosamente para matrícula de estudiante ID: {} y sección ID: {}", 
                       matricula.getEstudianteId(), matricula.getSeccionId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Evento de creación de matrícula publicado exitosamente");
        } catch (Exception e) {
            logger.error("Error al publicar evento de creación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al publicar evento de creación");
        }
    }

    @PutMapping("/events/update")
    public ResponseEntity<String> updateMatriculaEvent(@Valid @RequestBody Matricula matricula) {
        logger.info("PUT /api/matricula/events/update - Publicando evento de actualización de matrícula");
        try {
            if (matricula.getMatriculaId() == null) {
                logger.error("ID de matrícula requerido para evento de actualización");
                return ResponseEntity.badRequest()
                        .body("ID de matrícula es requerido para evento de actualización");
            }
            
            matriculaEventService.publishUpdateEvent(matricula);
            logger.info("Evento de actualización publicado exitosamente para matrícula con ID: {}", matricula.getMatriculaId());
            return ResponseEntity.ok("Evento de actualización de matrícula publicado exitosamente");
        } catch (Exception e) {
            logger.error("Error al publicar evento de actualización: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al publicar evento de actualización");
        }
    }

    @DeleteMapping("/events/delete")
    public ResponseEntity<String> deleteMatriculaEvent(@Valid @RequestBody Matricula matricula) {
        logger.info("DELETE /api/matricula/events/delete - Publicando evento de eliminación de matrícula");
        try {
            if (matricula.getMatriculaId() == null) {
                logger.error("ID de matrícula requerido para evento de eliminación");
                return ResponseEntity.badRequest()
                        .body("ID de matrícula es requerido para evento de eliminación");
            }
            
            matriculaEventService.publishDeleteEvent(matricula);
            logger.info("Evento de eliminación publicado exitosamente para matrícula con ID: {}", matricula.getMatriculaId());
            return ResponseEntity.ok("Evento de eliminación de matrícula publicado exitosamente");
        } catch (Exception e) {
            logger.error("Error al publicar evento de eliminación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al publicar evento de eliminación");
        }
    }

    @PatchMapping("/events/cancel")
    public ResponseEntity<String> cancelMatriculaEvent(@Valid @RequestBody Matricula matricula) {
        logger.info("PATCH /api/matricula/events/cancel - Publicando evento de cancelación de matrícula");
        try {
            if (matricula.getMatriculaId() == null) {
                logger.error("ID de matrícula requerido para evento de cancelación");
                return ResponseEntity.badRequest()
                        .body("ID de matrícula es requerido para evento de cancelación");
            }
            
            matriculaEventService.publishCancelEvent(matricula);
            logger.info("Evento de cancelación publicado exitosamente para matrícula con ID: {}", matricula.getMatriculaId());
            return ResponseEntity.ok("Evento de cancelación de matrícula publicado exitosamente");
        } catch (IllegalArgumentException e) {
            logger.warn("Error de validación en cancelación de matrícula: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            logger.error("Error de runtime en cancelación de matrícula: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor");
        } catch (Exception e) {
            logger.error("Error al publicar evento de cancelación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al publicar evento de cancelación");
        }
    }
}