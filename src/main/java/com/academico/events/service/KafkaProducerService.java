package com.academico.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Servicio genérico para enviar eventos a Kafka
 */
@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Método genérico para enviar mensajes a cualquier topic de Kafka
     */
    public void sendMessage(String topic, String key, Map<String, Object> eventData) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(eventData);
            
            CompletableFuture<SendResult<String, String>> future = 
                kafkaTemplate.send(topic, key, jsonMessage);
            
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.info("Evento enviado exitosamente: {} con offset: {}", 
                              eventData.get("eventType"), result.getRecordMetadata().offset());
                } else {
                    logger.error("Error al enviar evento: {} - Error: {}", 
                               eventData.get("eventType"), ex.getMessage());
                }
            });
            
        } catch (JsonProcessingException e) {
            logger.error("Error al serializar el evento a JSON: {}", e.getMessage());
            throw new RuntimeException("Error al procesar evento para Kafka", e);
        }
    }
}