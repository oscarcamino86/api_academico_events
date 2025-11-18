package com.academico.events.controller;

import com.academico.events.model.Estudiante;
import com.academico.events.service.EstudianteEventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstudianteController.class)
public class EstudianteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstudianteEventService estudianteEventService;

    @Autowired
    private ObjectMapper objectMapper;

    private Estudiante estudiante1;
    private Estudiante estudiante2;

    @BeforeEach
    void setUp() {
        estudiante1 = new Estudiante();
        estudiante1.setEstudianteId(1L);
        estudiante1.setNombre("Juan");
        estudiante1.setApellido("Pérez");
        estudiante1.setDni("12345678");
        estudiante1.setEmail("juan.perez@example.com");
        estudiante1.setTelefono("+51987654321");
        estudiante1.setFechaNacimiento(LocalDate.of(1995, 5, 15));
        estudiante1.setDireccion("Av. Principal 123, Lima");
        estudiante1.setFechaRegistro(LocalDateTime.now());
        estudiante1.setActivo(true);

        estudiante2 = new Estudiante();
        estudiante2.setEstudianteId(2L);
        estudiante2.setNombre("María");
        estudiante2.setApellido("García");
        estudiante2.setDni("87654321");
        estudiante2.setEmail("maria.garcia@example.com");
        estudiante2.setTelefono("+51987654322");
        estudiante2.setFechaNacimiento(LocalDate.of(1997, 8, 22));
        estudiante2.setDireccion("Jr. Los Olivos 456, Lima");
        estudiante2.setFechaRegistro(LocalDateTime.now());
        estudiante2.setActivo(true);
    }

    @Test
    void createEstudianteEvent_WithValidData_ShouldReturnCreated() throws Exception {
        doNothing().when(estudianteEventService).publishCreateEvent(any(Estudiante.class));

        String estudianteJson = objectMapper.writeValueAsString(estudiante1);

        mockMvc.perform(post("/api/estudiante/events/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(estudianteJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Evento de creación de estudiante publicado exitosamente"));
    }

    @Test
    void updateEstudianteEvent_WithValidData_ShouldReturnOk() throws Exception {
        doNothing().when(estudianteEventService).publishUpdateEvent(any(Estudiante.class));

        String estudianteJson = objectMapper.writeValueAsString(estudiante1);

        mockMvc.perform(put("/api/estudiante/events/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(estudianteJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Evento de actualización de estudiante publicado exitosamente"));
    }

    @Test
    void updateEstudianteEvent_WithoutId_ShouldReturnBadRequest() throws Exception {
        estudiante1.setEstudianteId(null);
        String estudianteJson = objectMapper.writeValueAsString(estudiante1);

        mockMvc.perform(put("/api/estudiante/events/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(estudianteJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID de estudiante es requerido para evento de actualización"));
    }

    @Test
    void deleteEstudianteEvent_WithValidData_ShouldReturnOk() throws Exception {
        doNothing().when(estudianteEventService).publishDeleteEvent(any(Estudiante.class));

        String estudianteJson = objectMapper.writeValueAsString(estudiante1);

        mockMvc.perform(delete("/api/estudiante/events/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(estudianteJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Evento de eliminación de estudiante publicado exitosamente"));
    }

    @Test
    void deleteEstudianteEvent_WithoutId_ShouldReturnBadRequest() throws Exception {
        estudiante1.setEstudianteId(null);
        String estudianteJson = objectMapper.writeValueAsString(estudiante1);

        mockMvc.perform(delete("/api/estudiante/events/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(estudianteJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID de estudiante es requerido para evento de eliminación"));
    }

    @Test
    void deactivateEstudianteEvent_WithValidData_ShouldReturnOk() throws Exception {
        doNothing().when(estudianteEventService).publishDeactivateEvent(any(Estudiante.class));

        String estudianteJson = objectMapper.writeValueAsString(estudiante1);

        mockMvc.perform(patch("/api/estudiante/events/deactivate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(estudianteJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Evento de desactivación de estudiante publicado exitosamente"));
    }

    @Test
    void createEstudianteEvent_WithException_ShouldReturnInternalServerError() throws Exception {
        doThrow(new RuntimeException("Kafka error")).when(estudianteEventService).publishCreateEvent(any(Estudiante.class));

        String estudianteJson = objectMapper.writeValueAsString(estudiante1);

        mockMvc.perform(post("/api/estudiante/events/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(estudianteJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error al publicar evento de creación"));
    }
}