package com.academico.events.controller;

import com.academico.events.model.Matricula;
import com.academico.events.service.MatriculaEventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatriculaController.class)
class MatriculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatriculaEventService matriculaEventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMatriculaEvent_ShouldReturnCreated_WhenValidMatricula() throws Exception {
        Matricula matricula = new Matricula();
        matricula.setEstudianteId(1L);
        matricula.setSeccionId(1L);
        matricula.setCosto(new BigDecimal("100.00"));
        matricula.setFechaMatricula(LocalDate.now());
        matricula.setEstado("PENDIENTE");

        doNothing().when(matriculaEventService).publishCreateEvent(any(Matricula.class));

        mockMvc.perform(post("/api/matricula/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Evento de creación de matrícula publicado exitosamente"));

        verify(matriculaEventService, times(1)).publishCreateEvent(any(Matricula.class));
    }

    @Test
    void createMatriculaEvent_ShouldReturnBadRequest_WhenInvalidMatricula() throws Exception {
        Matricula matricula = new Matricula();
        // No se establecen campos requeridos

        mockMvc.perform(post("/api/matricula/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isBadRequest());

        verify(matriculaEventService, never()).publishCreateEvent(any(Matricula.class));
    }

    @Test
    void updateMatriculaEvent_ShouldReturnOk_WhenValidMatriculaWithId() throws Exception {
        Matricula matricula = new Matricula();
        matricula.setMatriculaId(1L);
        matricula.setEstudianteId(1L);
        matricula.setSeccionId(1L);
        matricula.setCosto(new BigDecimal("150.00"));
        matricula.setFechaMatricula(LocalDate.now());
        matricula.setEstado("CONFIRMADA");

        doNothing().when(matriculaEventService).publishUpdateEvent(any(Matricula.class));

        mockMvc.perform(put("/api/matricula/events/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isOk())
                .andExpect(content().string("Evento de actualización de matrícula publicado exitosamente"));

        verify(matriculaEventService, times(1)).publishUpdateEvent(any(Matricula.class));
    }

    @Test
    void updateMatriculaEvent_ShouldReturnBadRequest_WhenMatriculaWithoutId() throws Exception {
        Matricula matricula = new Matricula();
        matricula.setEstudianteId(1L);
        matricula.setSeccionId(1L);
        matricula.setCosto(new BigDecimal("150.00"));

        mockMvc.perform(put("/api/matricula/events/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID de matrícula es requerido para evento de actualización"));

        verify(matriculaEventService, never()).publishUpdateEvent(any(Matricula.class));
    }

    @Test
    void deleteMatriculaEvent_ShouldReturnOk_WhenValidMatriculaWithId() throws Exception {
        Matricula matricula = new Matricula();
        matricula.setMatriculaId(1L);
        matricula.setEstudianteId(1L);
        matricula.setSeccionId(1L);
        matricula.setCosto(new BigDecimal("100.00"));
        matricula.setFechaMatricula(LocalDate.now());
        matricula.setEstado("PENDIENTE");

        doNothing().when(matriculaEventService).publishDeleteEvent(any(Matricula.class));

        mockMvc.perform(delete("/api/matricula/events/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isOk())
                .andExpect(content().string("Evento de eliminación de matrícula publicado exitosamente"));

        verify(matriculaEventService, times(1)).publishDeleteEvent(any(Matricula.class));
    }

    @Test
    void cancelMatriculaEvent_ShouldReturnOk_WhenValidMatriculaWithId() throws Exception {
        Matricula matricula = new Matricula();
        matricula.setMatriculaId(1L);
        matricula.setEstudianteId(1L);
        matricula.setSeccionId(1L);
        matricula.setCosto(new BigDecimal("100.00"));
        matricula.setFechaMatricula(LocalDate.now());
        matricula.setEstado("PENDIENTE");

        doNothing().when(matriculaEventService).publishCancelEvent(any(Matricula.class));

        mockMvc.perform(patch("/api/matricula/events/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isOk())
                .andExpect(content().string("Evento de cancelación de matrícula publicado exitosamente"));

        verify(matriculaEventService, times(1)).publishCancelEvent(any(Matricula.class));
    }

    @Test
    void cancelMatriculaEvent_ShouldReturnBadRequest_WhenMatriculaWithoutId() throws Exception {
        Matricula matricula = new Matricula();
        matricula.setEstudianteId(1L);
        matricula.setSeccionId(1L);
        matricula.setCosto(new BigDecimal("100.00"));

        mockMvc.perform(patch("/api/matricula/events/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matricula)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID de matrícula es requerido para evento de cancelación"));

        verify(matriculaEventService, never()).publishCancelEvent(any(Matricula.class));
    }
}