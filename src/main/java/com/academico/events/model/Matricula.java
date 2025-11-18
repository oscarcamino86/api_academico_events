package com.academico.events.model;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa una matrícula en el sistema académico.
 * Esta clase se usa únicamente para eventos, no incluye anotaciones JPA.
 */
public class Matricula {

    @JsonProperty("matriculaId")
    private Long matriculaId;

    @NotNull(message = "El ID del estudiante es obligatorio")
    @JsonProperty("estudianteId")
    private Long estudianteId;

    @NotNull(message = "El ID de la sección es obligatorio")
    @JsonProperty("seccionId")
    private Long seccionId;

    @NotNull(message = "La fecha de matrícula es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fechaMatricula")
    private LocalDate fechaMatricula;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20, message = "El estado no puede tener más de 20 caracteres")
    @Pattern(regexp = "^(PENDIENTE|CONFIRMADA|CANCELADA|COMPLETADA)$", 
             message = "El estado debe ser: PENDIENTE, CONFIRMADA, CANCELADA o COMPLETADA")
    @JsonProperty("estado")
    private String estado;

    @NotNull(message = "El costo es obligatorio")
    @DecimalMin(value = "0.00", message = "El costo debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 2, message = "El costo debe tener máximo 10 dígitos enteros y 2 decimales")
    @JsonProperty("costo")
    private BigDecimal costo;

    @Size(max = 50, message = "El método de pago no puede tener más de 50 caracteres")
    @JsonProperty("metodoPago")
    private String metodoPago;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("fechaRegistro")
    private LocalDateTime fechaRegistro;

    // Constructor vacío
    public Matricula() {
        this.fechaRegistro = LocalDateTime.now();
        this.fechaMatricula = LocalDate.now();
        this.estado = "PENDIENTE";
    }

    // Constructor con parámetros básicos
    public Matricula(Long estudianteId, Long seccionId, BigDecimal costo) {
        this();
        this.estudianteId = estudianteId;
        this.seccionId = seccionId;
        this.costo = costo;
    }

    // Getters y Setters
    public Long getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Long matriculaId) {
        this.matriculaId = matriculaId;
    }

    public Long getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Long estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Long getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(Long seccionId) {
        this.seccionId = seccionId;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "matriculaId=" + matriculaId +
                ", estudianteId=" + estudianteId +
                ", seccionId=" + seccionId +
                ", fechaMatricula=" + fechaMatricula +
                ", estado='" + estado + '\'' +
                ", costo=" + costo +
                ", metodoPago='" + metodoPago + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matricula matricula = (Matricula) o;

        if (matriculaId != null ? !matriculaId.equals(matricula.matriculaId) : matricula.matriculaId != null)
            return false;
        if (estudianteId != null ? !estudianteId.equals(matricula.estudianteId) : matricula.estudianteId != null)
            return false;
        return seccionId != null ? seccionId.equals(matricula.seccionId) : matricula.seccionId == null;
    }

    @Override
    public int hashCode() {
        int result = matriculaId != null ? matriculaId.hashCode() : 0;
        result = 31 * result + (estudianteId != null ? estudianteId.hashCode() : 0);
        result = 31 * result + (seccionId != null ? seccionId.hashCode() : 0);
        return result;
    }
}