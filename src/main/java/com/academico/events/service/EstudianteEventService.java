package com.academico.events.service;

import com.academico.events.model.Estudiante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para manejar eventos de estudiante y publicarlos en Kafka
 */
@Service
public class EstudianteEventService {

    private static final Logger logger = LoggerFactory.getLogger(EstudianteEventService.class);
    private static final String TOPIC_NAME = "estudiante-events";
    
    private final KafkaProducerService kafkaProducerService;

    public EstudianteEventService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
     * Publica un evento de creación de estudiante
     */
    public void publishCreateEvent(Estudiante estudiante) {
        logger.info("Publicando evento CREATE para estudiante con DNI: {}", estudiante.getDni());
        
        estudiante.setFechaRegistro(LocalDateTime.now());
        
        Map<String, Object> eventData = createEventData("CREATE", estudiante);
        String key = "estudiante-" + System.currentTimeMillis();
        
        kafkaProducerService.sendMessage(TOPIC_NAME, key, eventData);
        logger.info("Evento CREATE publicado exitosamente para estudiante con DNI: {}", estudiante.getDni());
    }

    /**
     * Publica un evento de actualización de estudiante
     */
    public void publishUpdateEvent(Estudiante estudiante) {
        logger.info("Publicando evento UPDATE para estudiante con ID: {}", estudiante.getEstudianteId());
        
        estudiante.setFechaRegistro(LocalDateTime.now());
        
        Map<String, Object> eventData = createEventData("UPDATE", estudiante);
        String key = "estudiante-" + estudiante.getEstudianteId();
        
        kafkaProducerService.sendMessage(TOPIC_NAME, key, eventData);
        logger.info("Evento UPDATE publicado exitosamente para estudiante con ID: {}", estudiante.getEstudianteId());
    }

    /**
     * Publica un evento de eliminación de estudiante
     */
    public void publishDeleteEvent(Estudiante estudiante) {
        logger.info("Publicando evento DELETE para estudiante con ID: {}", estudiante.getEstudianteId());
        
        estudiante.setFechaRegistro(LocalDateTime.now());
        
        Map<String, Object> eventData = createEventData("DELETE", estudiante);
        String key = "estudiante-" + estudiante.getEstudianteId();
        
        kafkaProducerService.sendMessage(TOPIC_NAME, key, eventData);
        logger.info("Evento DELETE publicado exitosamente para estudiante con ID: {}", estudiante.getEstudianteId());
    }

    /**
     * Publica un evento de desactivación de estudiante
     */
    public void publishDeactivateEvent(Estudiante estudiante) {
        logger.info("Publicando evento DEACTIVATE para estudiante con ID: {}", estudiante.getEstudianteId());
        
        estudiante.setFechaRegistro(LocalDateTime.now());
        estudiante.setActivo(false);
        
        Map<String, Object> eventData = createEventData("DEACTIVATE", estudiante);
        String key = "estudiante-" + estudiante.getEstudianteId();
        
        kafkaProducerService.sendMessage(TOPIC_NAME, key, eventData);
        logger.info("Evento DEACTIVATE publicado exitosamente para estudiante con ID: {}", estudiante.getEstudianteId());
    }

    /**
     * Crea el mapa de datos del evento
     */
    private Map<String, Object> createEventData(String eventType, Estudiante estudiante) {
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("eventType", eventType);
        eventData.put("timestamp", LocalDateTime.now().toString());
        eventData.put("estudianteId", estudiante.getEstudianteId());
        eventData.put("nombre", estudiante.getNombre());
        eventData.put("apellido", estudiante.getApellido());
        eventData.put("dni", estudiante.getDni());
        eventData.put("email", estudiante.getEmail());
        eventData.put("telefono", estudiante.getTelefono());
        eventData.put("fechaNacimiento", estudiante.getFechaNacimiento() != null ? estudiante.getFechaNacimiento().toString() : null);
        eventData.put("direccion", estudiante.getDireccion());
        eventData.put("fechaRegistro", estudiante.getFechaRegistro() != null ? estudiante.getFechaRegistro().toString() : null);
        eventData.put("activo", estudiante.getActivo());
        
        return eventData;
    }
}