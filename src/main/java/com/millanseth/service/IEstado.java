package com.millanseth.service;

import com.millanseth.model.dto.EstadoDto;
import com.millanseth.model.entity.Estado;

import java.util.List;

public interface IEstado {
    List<Estado> listAll();
    Estado save(Estado estado);
    Estado findById(Integer id);
     void delete(Estado estado);
}
