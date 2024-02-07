package com.millanseth.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class AsentamientoDto implements Serializable {
    private String asentamiento;
    private Integer codigoPostal;
    private String municipio;
    private String estado;
    private String edoAbrev;
}
