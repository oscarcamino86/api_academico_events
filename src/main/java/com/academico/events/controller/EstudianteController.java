package com.academico.events.controller;

import com.academico.events.model.Estudiante;
import com.academico.events.service.EstudianteEventService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estudiante")
@CrossOrigin(origins = "*")
public class EstudianteController {

    private static final Logger logger = LoggerFactory.getLogger(EstudianteController.class);
    
    private final EstudianteEventService estudianteEventService;

    public EstudianteController(EstudianteEventService estudianteEventService) {
        this.estudianteEventService = estudianteEventService;
    }

    @PostMapping("/events/create")
    public ResponseEntity<String> createEstudianteEvent(@Valid @RequestBody Estudiante estudiante) {
        logger.info("POST /api/estudiante/events/create - Publicando evento de creación de estudiante");
        try {
            estudianteEventService.publishCreateEvent(estudiante);
            logger.info("Evento de creación publicado exitosamente para estudiante con DNI: {}", estudiante.getDni());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Evento de creación de estudiante publicado exitosamente");
        } catch (Exception e) {
            logger.error("Error al publicar evento de creación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al publicar evento de creación");
        }
    }

    @PutMapping("/events/update")
    public ResponseEntity<String> updateEstudianteEvent(@Valid @RequestBody Estudiante estudiante) {
        logger.info("PUT /api/estudiante/events/update - Publicando evento de actualización de estudiante");
        try {
            if (estudiante.getEstudianteId() == null) {
                logger.error("ID de estudiante requerido para evento de actualización");
                return ResponseEntity.badRequest()
                        .body("ID de estudiante es requerido para evento de actualización");
            }
            
            estudianteEventService.publishUpdateEvent(estudiante);
            logger.info("Evento de actualización publicado exitosamente para estudiante con ID: {}", estudiante.getEstudianteId());
            return ResponseEntity.ok("Evento de actualización de estudiante publicado exitosamente");
        } catch (Exception e) {
            logger.error("Error al publicar evento de actualización: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al publicar evento de actualización");
        }
    }

    @DeleteMapping("/events/delete")
    public ResponseEntity<String> deleteEstudianteEvent(@Valid @RequestBody Estudiante estudiante) {
        logger.info("DELETE /api/estudiante/events/delete - Publicando evento de eliminación de estudiante");
        try {
            if (estudiante.getEstudianteId() == null) {
                logger.error("ID de estudiante requerido para evento de eliminación");
                return ResponseEntity.badRequest()
                        .body("ID de estudiante es requerido para evento de eliminación");
            }
            
            estudianteEventService.publishDeleteEvent(estudiante);
            logger.info("Evento de eliminación publicado exitosamente para estudiante con ID: {}", estudiante.getEstudianteId());
            return ResponseEntity.ok("Evento de eliminación de estudiante publicado exitosamente");
        } catch (Exception e) {
            logger.error("Error al publicar evento de eliminación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al publicar evento de eliminación");
        }
    }

    @PatchMapping("/events/deactivate")
    public ResponseEntity<String> deactivateEstudianteEvent(@Valid @RequestBody Estudiante estudiante) {
        logger.info("PATCH /api/estudiante/events/deactivate - Publicando evento de desactivación de estudiante");
        try {
            if (estudiante.getEstudianteId() == null) {
                logger.error("ID de estudiante requerido para evento de desactivación");
                return ResponseEntity.badRequest()
                        .body("ID de estudiante es requerido para evento de desactivación");
            }
            
            estudianteEventService.publishDeactivateEvent(estudiante);
            logger.info("Evento de desactivación publicado exitosamente para estudiante con ID: {}", estudiante.getEstudianteId());
            return ResponseEntity.ok("Evento de desactivación de estudiante publicado exitosamente");
        } catch (Exception e) {
            logger.error("Error al publicar evento de desactivación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al publicar evento de desactivación");
        }
    }
}