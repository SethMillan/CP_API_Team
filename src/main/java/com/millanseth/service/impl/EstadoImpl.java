package com.millanseth.service.impl;

import com.millanseth.model.dao.EstadoDAO;
import com.millanseth.model.dto.EstadoDto;
import com.millanseth.model.entity.Estado;
import com.millanseth.service.IEstado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoImpl implements IEstado {
    @Autowired
    private EstadoDAO estadoDAO;

    @Transactional
    @Override
    public Estado save(Estado estado) {
        /*Estado estado =Estado.builder()
                .id(estadoDto.getIdEdo())
                .Estado(estadoDto.getEstado())
                .build();*/
        return estadoDAO.save(estado);
    }


    @Transactional(readOnly = true)
    @Override
    public Estado findById(Integer id) {
        return estadoDAO.findById(id).orElse(null);
    }


    @Transactional
    @Override
    public void delete(Estado estado) {
        estadoDAO.delete(estado);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Estado> listAll() {
        return (List)estadoDAO.findAll();
    }
}
