package com.millanseth.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class MunicipioDto implements Serializable {
    private Integer idEdo;
    private Integer idMcpio;
    private String municipio;
}
