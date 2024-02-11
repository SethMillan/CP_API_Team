package com.millanseth.model.dao;

import com.millanseth.model.entity.Municipio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MunicipioDAO extends CrudRepository<Municipio,Integer> {
    @Query("SELECT m FROM Municipio m JOIN FETCH m.estado WHERE m.estado.id = :estadoId")

    List<Municipio> findEstadoById(@Param("estadoId")Integer id_Edo);
}
