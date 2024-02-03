package com.millanseth.model.dao;

import com.millanseth.model.entity.CodigoPostal;
import com.millanseth.model.entity.Municipio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodigoPostalDAO extends CrudRepository<CodigoPostal,Integer> {
    //@Query("SELECT m FROM CodigoPostal m JOIN FETCH m.municipio WHERE m.municipio.id = :municipioID AND m.municipio.estado.idEdo = :estadoID")
    //List<CodigoPostal> findMunicipioById(@Param("municipioID") Integer id_Mcpio, @Param("estadoID") Integer id_Edo);
    @Query("SELECT cp FROM CodigoPostal cp JOIN FETCH cp.municipio m WHERE m.id = :municipioID AND m.estado.id = :estadoID")
    List<CodigoPostal> findCodigoPostalByMunicipioAndEstado(@Param("municipioID") Integer idMunicipio, @Param("estadoID") Integer idEstado);
    //@Query("SELECT cp FROM CodigoPostal cp JOIN FETCH cp.municipio m WHERE m.estado.idEdo = :estadoID")
    //List<CodigoPostal> findCodigoPostalByMunicipioAndEstado(@Param("estadoID") Integer idEstado);

}
