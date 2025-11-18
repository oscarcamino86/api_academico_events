package com.academico.events.service;

import com.academico.events.model.Matricula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatriculaEventServiceTest {

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private MatriculaEventService matriculaEventService;

    private Matricula matricula;

    @BeforeEach
    void setUp() {
        matricula = new Matricula();
        matricula.setMatriculaId(1L);
        matricula.setEstudianteId(100L);
        matricula.setSeccionId(10L);
        matricula.setFechaMatricula(LocalDate.of(2024, 1, 15));
        matricula.setEstado("PENDIENTE");
        matricula.setCosto(new BigDecimal("250.00"));
        matricula.setMetodoPago("TARJETA_CREDITO");
    }

    @Test
    void publishCreateEvent_ShouldCallKafkaProducerService() {
        // Arrange
        doNothing().when(kafkaProducerService).sendMessage(anyString(), anyString(), any(Map.class));

        // Act
        matriculaEventService.publishCreateEvent(matricula);

        // Assert
        verify(kafkaProducerService, times(1)).sendMessage(
                eq("matricula-events"), 
                startsWith("matricula-"), 
                any(Map.class)
        );
    }

    @Test
    void publishUpdateEvent_ShouldCallKafkaProducerService() {
        // Arrange
        doNothing().when(kafkaProducerService).sendMessage(anyString(), anyString(), any(Map.class));

        // Act
        matriculaEventService.publishUpdateEvent(matricula);

        // Assert
        verify(kafkaProducerService, times(1)).sendMessage(
                eq("matricula-events"), 
                eq("matricula-1"), 
                any(Map.class)
        );
    }

    @Test
    void publishDeleteEvent_ShouldCallKafkaProducerService() {
        // Arrange
        doNothing().when(kafkaProducerService).sendMessage(anyString(), anyString(), any(Map.class));

        // Act
        matriculaEventService.publishDeleteEvent(matricula);

        // Assert
        verify(kafkaProducerService, times(1)).sendMessage(
                eq("matricula-events"), 
                eq("matricula-1"), 
                any(Map.class)
        );
    }

    @Test
    void publishCancelEvent_ShouldCallKafkaProducerService() {
        // Arrange
        doNothing().when(kafkaProducerService).sendMessage(anyString(), anyString(), any(Map.class));

        // Act
        matriculaEventService.publishCancelEvent(matricula);

        // Assert
        verify(kafkaProducerService, times(1)).sendMessage(
                eq("matricula-events"), 
                eq("matricula-1"), 
                any(Map.class)
        );
        
        // Verify that the status was changed to CANCELADA
        assert matricula.getEstado().equals("CANCELADA");
    }

    @Test
    void publishCreateEvent_ShouldHandleMatriculaWithoutId() {
        // Arrange
        matricula.setMatriculaId(null);
        doNothing().when(kafkaProducerService).sendMessage(anyString(), anyString(), any(Map.class));

        // Act
        matriculaEventService.publishCreateEvent(matricula);

        // Assert
        verify(kafkaProducerService, times(1)).sendMessage(
                eq("matricula-events"), 
                startsWith("matricula-"), 
                any(Map.class)
        );
    }

    @Test
    void publishUpdateEvent_ShouldHandleMatriculaWithNullFields() {
        // Arrange
        matricula.setMetodoPago(null);
        doNothing().when(kafkaProducerService).sendMessage(anyString(), anyString(), any(Map.class));

        // Act
        matriculaEventService.publishUpdateEvent(matricula);

        // Assert
        verify(kafkaProducerService, times(1)).sendMessage(
                eq("matricula-events"), 
                eq("matricula-1"), 
                any(Map.class)
        );
    }
}