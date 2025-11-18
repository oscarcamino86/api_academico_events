package com.academico.events.service;

import com.academico.events.model.Matricula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para manejar eventos de matrícula y publicarlos en Kafka
 */
@Service
public class MatriculaEventService {

    private static final Logger logger = LoggerFactory.getLogger(MatriculaEventService.class);
    private static final String TOPIC_NAME = "matricula-events";

    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * Publica un evento de creación de matrícula
     */
    public void publishCreateEvent(Matricula matricula) {
        logger.info("Publicando evento CREATE para matrícula con estudiante ID: {} y sección ID: {}", 
                   matricula.getEstudianteId(), matricula.getSeccionId());
        
        matricula.setFechaRegistro(LocalDateTime.now());
        
        Map<String, Object> eventData = createEventData("CREATE", matricula);
        String key = "matricula-" + System.currentTimeMillis();
        
        kafkaProducerService.sendMessage(TOPIC_NAME, key, eventData);
        logger.info("Evento CREATE publicado exitosamente para matrícula con estudiante ID: {} y sección ID: {}", 
                   matricula.getEstudianteId(), matricula.getSeccionId());
    }

    /**
     * Publica un evento de actualización de matrícula
     */
    public void publishUpdateEvent(Matricula matricula) {
        logger.info("Publicando evento UPDATE para matrícula con ID: {}", matricula.getMatriculaId());
        
        matricula.setFechaRegistro(LocalDateTime.now());
        
        Map<String, Object> eventData = createEventData("UPDATE", matricula);
        String key = "matricula-" + matricula.getMatriculaId();
        
        kafkaProducerService.sendMessage(TOPIC_NAME, key, eventData);
        logger.info("Evento UPDATE publicado exitosamente para matrícula con ID: {}", matricula.getMatriculaId());
    }

    /**
     * Publica un evento de eliminación de matrícula
     */
    public void publishDeleteEvent(Matricula matricula) {
        logger.info("Publicando evento DELETE para matrícula con ID: {}", matricula.getMatriculaId());
        
        matricula.setFechaRegistro(LocalDateTime.now());
        
        Map<String, Object> eventData = createEventData("DELETE", matricula);
        String key = "matricula-" + matricula.getMatriculaId();
        
        kafkaProducerService.sendMessage(TOPIC_NAME, key, eventData);
        logger.info("Evento DELETE publicado exitosamente para matrícula con ID: {}", matricula.getMatriculaId());
    }

    /**
     * Publica un evento de cancelación de matrícula
     */
    public void publishCancelEvent(Matricula matricula) {
        logger.info("Publicando evento CANCEL para matrícula con ID: {}", matricula.getMatriculaId());
        
        matricula.setFechaRegistro(LocalDateTime.now());
        matricula.setEstado("CANCELADA");
        
        Map<String, Object> eventData = createEventData("CANCEL", matricula);
        String key = "matricula-" + matricula.getMatriculaId();
        
        kafkaProducerService.sendMessage(TOPIC_NAME, key, eventData);
        logger.info("Evento CANCEL publicado exitosamente para matrícula con ID: {}", matricula.getMatriculaId());
    }

    /**
     * Crea el mapa de datos del evento
     */
    private Map<String, Object> createEventData(String eventType, Matricula matricula) {
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("eventType", eventType);
        eventData.put("timestamp", LocalDateTime.now().toString());
        eventData.put("matriculaId", matricula.getMatriculaId());
        eventData.put("estudianteId", matricula.getEstudianteId());
        eventData.put("seccionId", matricula.getSeccionId());
        eventData.put("fechaMatricula", matricula.getFechaMatricula() != null ? matricula.getFechaMatricula().toString() : null);
        eventData.put("estado", matricula.getEstado());
        eventData.put("costo", matricula.getCosto());
        eventData.put("metodoPago", matricula.getMetodoPago());
        eventData.put("fechaRegistro", matricula.getFechaRegistro() != null ? matricula.getFechaRegistro().toString() : null);
        
        return eventData;
    }
}