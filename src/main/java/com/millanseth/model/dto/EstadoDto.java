package com.millanseth.model.dto;


import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
public class EstadoDto implements Serializable {

    private Integer idEdo;
    private String estado;
    private String edoAbrev;
}
