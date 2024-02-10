package com.millanseth.payload;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data@ToString@Builder
public class MensajeResponse implements Serializable {
    private Boolean error;
    private String mensaje;
    private Object object;
    private Object estado;
    private Object estados;
    private Object municipios;
    private Object municipio;
    private Object asentamientos;
    private Object asentamiento;
    private Object codigoPostal;
    private Object codigosPostales;
}
