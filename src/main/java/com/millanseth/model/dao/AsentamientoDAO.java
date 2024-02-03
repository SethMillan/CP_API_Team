package com.millanseth.model.dao;

import com.millanseth.model.entity.Asentamiento;
import com.millanseth.model.entity.Municipio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsentamientoDAO extends CrudRepository <Asentamiento,Integer>{
    @Query("SELECT m FROM Asentamiento m JOIN FETCH m.codigoPostal WHERE m.codigoPostal.cp = :cpid")
    List<Asentamiento> findCPById(@Param("cpid")Integer cp);
    @Query("SELECT m FROM Asentamiento m JOIN FETCH m.codigoPostal WHERE m.codigoPostal.municipio.id= :mcpioId AND m.codigoPostal.municipio.estado.id =:edoId")
    List<Asentamiento> findCPByMcpioAndEdo(@Param("mcpioId")Integer idMcpio, @Param("edoId") Integer idEdo);


}
