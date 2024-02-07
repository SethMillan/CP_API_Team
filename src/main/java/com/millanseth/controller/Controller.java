package com.millanseth.controller;

import com.millanseth.model.dto.AsentamientoDto;
import com.millanseth.model.dto.CodigoPostalDto;
import com.millanseth.model.dto.EstadoDto;
import com.millanseth.model.dto.MunicipioDto;
import com.millanseth.model.entity.Asentamiento;
import com.millanseth.model.entity.CodigoPostal;
import com.millanseth.model.entity.Estado;
import com.millanseth.model.entity.Municipio;
import com.millanseth.payload.MensajeResponse;
import com.millanseth.service.IAsentamiento;
import com.millanseth.service.ICodigoPostal;
import com.millanseth.service.IEstado;
import com.millanseth.service.IMunicipio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")//Esta es la direccion a donde nos vamos a conectar, indicamos que es una api y que es la version 1
public class Controller {
    @Autowired//inyeccion de dependencias
    private IEstado estadoService;
    @Autowired
    private IMunicipio municipioService;
    @Autowired
    private ICodigoPostal codigoService;
    @Autowired
    private IAsentamiento asentamientoService;
    //PARA MOSTRAR TODA LA INFORMACION DE NUESTRAS TABLAS SIN FILTROS....
    @GetMapping("estados")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showAll() {
        try{
        List<Estado> listaEstados= estadoService.listAll();
        if (listaEstados==null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .error(false)
                            .mensaje("No hay estados registrados")
                            .object(null).build(),
                    HttpStatus.OK);
        }else {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .error(false)
                            .mensaje("Estados encontrados :" + listaEstados.size())
                            .object(listaEstados)
                            .build()
                    , HttpStatus.OK);
        }
        }catch(Exception exDt){
            return new ResponseEntity<>(MensajeResponse.builder().error(true).mensaje("Error encontrado "+exDt).object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("municipios")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showAllMunicipios(){
        try{
        List<MunicipioDto> listaMunicipios= municipioService.listAll().stream().map(municipio -> MunicipioDto.builder().idEdo(municipio.getEstado().getId()).idMcpio(municipio.getId()).municipio(municipio.getMunicipio()).build()).toList();
        if (listaMunicipios==null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .error(false)
                            .mensaje("No hay estados registrados")
                            .object(null).build(),
                    HttpStatus.OK);
        }else{
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .error(false)
                            .mensaje("Estados encontrados : "+listaMunicipios.size())
                            .object(listaMunicipios)
                            .build()
                    ,HttpStatus.OK);
        }
        }catch (Exception ext){
            return new ResponseEntity<>(MensajeResponse.builder().error(true).mensaje("Error encontrado "+ext).object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("codigospostales")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showAllCp(){
        try {
            List<CodigoPostal> listaCP = codigoService.listAll();
            List<CodigoPostalDto> listaDto = listaCP.stream()
                    .map(codigoPostal -> CodigoPostalDto.builder()
                            .idEdo(codigoPostal.getMunicipio().getEstado().getId())
                            .estado(codigoPostal.getMunicipio().getEstado().getEstado())
                            .idMcpio(codigoPostal.getMunicipio().getId())
                            .municipio(codigoPostal.getMunicipio().getMunicipio())
                            .codigoPostal(codigoPostal.getCp())
                            .build()
                    ).toList();
            return new ResponseEntity<>(MensajeResponse.builder().error(false).mensaje("Codigos totales "+listaDto.size()).object(listaDto).build(),HttpStatus.OK);
        }catch (Exception ext ){
            return new ResponseEntity<>(MensajeResponse.builder().error(true).mensaje("Error encontrado "+ext).object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("asentamientos")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showAllAsenta(){
        try {
            List<Asentamiento> listaAsenta = asentamientoService.listAll();
            List<AsentamientoDto> listaDto = listaAsenta.stream()
                    .map(asentamiento -> AsentamientoDto.builder()
                            .estado(asentamiento.getCodigoPostal().getMunicipio().getEstado().getEstado())
                            .municipio(asentamiento.getCodigoPostal().getMunicipio().getMunicipio())
                            .codigoPostal(asentamiento.getCodigoPostal().getCp())
                            .asentamiento(asentamiento.getAsentamiento())
                            .build()
                    ).toList();
            return new ResponseEntity<>(MensajeResponse.builder().error(false).mensaje("Codigos totales "+listaDto.size()).object(listaDto).build(),HttpStatus.OK);
        }catch (Exception ext ){
            return new ResponseEntity<>(MensajeResponse.builder().error(true).mensaje("Error encontrado "+ext).object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //PARA MOSTRAR LA INFORMACION DE NUESTRAS TABLAS FILTRANDO LA INFORMACION

    @GetMapping("estado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        try {
            Estado estado = estadoService.findById(id);
            if (estado == null) {
                return new ResponseEntity<>(
                        MensajeResponse.builder().error(true).mensaje("El registro que intenta buscar no existe").object(null).build(),
                        HttpStatus.NOT_FOUND);//en caso de no encontrarlo manda un objeto nulo y un mensaje de error
            } else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .error(false)
                                .mensaje("Encontrado")
                                .object(
                                        EstadoDto.builder()
                                                .idEdo(estado.getId())
                                                .estado(estado.getEstado())
                                                .build()
                                ).build()
                        , HttpStatus.OK);
            }
        }catch (Exception ext){
            return new ResponseEntity<>(MensajeResponse.builder().error(true).mensaje("Error encontrado "+ext).object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("municipios/estado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> mostrarMcpios(@PathVariable Integer id){
           try{
               List<Municipio> listMunicipios = municipioService.listAllById(id);
                if (listMunicipios==null){
                    return new ResponseEntity<>(
                            MensajeResponse.builder()
                                    .error(true)
                                    .mensaje("No hay municipios registrados")
                                    .object(null).build(),
                            HttpStatus.METHOD_NOT_ALLOWED);
                }else{
                    List<MunicipioDto>municipiosDTO = listMunicipios.stream()
                            .map(municipio -> MunicipioDto.builder()
                                    .idEdo(municipio.getEstado().getId())
                                    .idMcpio(municipio.getId())
                                    .municipio(municipio.getMunicipio())
                                    .build())
                            .collect(Collectors.toList());
                    int tamaño= municipiosDTO.size();
                    return new ResponseEntity<>(
                            MensajeResponse.builder()
                                    .error(false)
                                    .mensaje("Municipios encontrados : "+tamaño)
                                    .object(municipiosDTO)
                                    .build()
                            ,HttpStatus.OK);
                }
            }catch (Exception exDt){
                return new ResponseEntity<>(
                    MensajeResponse.builder().error(true).mensaje(exDt.getMessage()).object(null).build(),
                        HttpStatus.INTERNAL_SERVER_ERROR);//el http response que mandamos sera uno de error
        }
    }
    @GetMapping("codigospostales/municipio/{idMcpio}/estado/{idEstado}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?>showFilterCP(@PathVariable Integer idEstado, @PathVariable Integer idMcpio){
        try {
            List<CodigoPostal> listaCodigos = codigoService.listAllById(idMcpio, idEstado);
            if(listaCodigos!=null) {
                List<CodigoPostalDto> codigoDto;
                codigoDto = listaCodigos.stream()
                        .map(codigopostal -> CodigoPostalDto.builder()
                                .idEdo(codigopostal.getMunicipio().getEstado().getId())
                                .idMcpio(codigopostal.getMunicipio().getId())
                                .estado(codigopostal.getMunicipio().getEstado().getEstado())
                                .municipio(codigopostal.getMunicipio().getMunicipio())
                                .codigoPostal(codigopostal.getCp())
                                .build())
                        .collect(Collectors.toList());
                return new ResponseEntity<>(MensajeResponse.builder().error(false).mensaje("Numero de CP encontrados : " + codigoDto.size()).object(codigoDto).build(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(MensajeResponse.builder().error(true).mensaje("No se encontraron codigos postales en ese municipio").object(null).build(),HttpStatus.NOT_FOUND);
            }
        }catch (Exception exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder().error(true).mensaje(exDt.getMessage()).object(null).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);//el http response que mandamos sera uno de error
        }
    }

    @GetMapping("asentamientos/codigopostal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showFilterAsenta(@PathVariable Integer id){
        try {
            List<Asentamiento> asenta = asentamientoService.listAllById(id);//aqui obviamente hay un error por lo  ya mencionado
            if(asenta!=null) {
                List<AsentamientoDto> asentaDto = asenta.stream().map(asentamiento -> AsentamientoDto
                        .builder()
                        .estado(asentamiento.getCodigoPostal().getMunicipio().getEstado().getEstado())
                        .municipio(asentamiento.getCodigoPostal().getMunicipio().getMunicipio())
                        .codigoPostal(asentamiento.getCodigoPostal().getCp())
                        .asentamiento(asentamiento.getAsentamiento())
                        .build()).toList();
                return new ResponseEntity<>(MensajeResponse.builder().error(false).mensaje("Asentamientos encontrados: "+asentaDto.size()).object(asentaDto).build(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(MensajeResponse.builder().error(true).mensaje("No se encontraron asentamientos").object(null).build(),HttpStatus.NOT_FOUND);
            }
        }catch (Exception exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder().error(true).mensaje(exDt.getMessage()).object(null).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("asentamientos/municipio/{idmcpio}/estado/{idEdo}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?>showCPFilteredByEdoAndMcpio(@PathVariable Integer idmcpio, @PathVariable Integer idEdo){
        try{
            List<Asentamiento> asentamientosLista =asentamientoService.findCPByMcpioAndEdo(idmcpio,idEdo);
            if(asentamientosLista!=null){
                List<AsentamientoDto> asentaDto = asentamientosLista.stream().map(asentamiento -> AsentamientoDto
                        .builder()
                        .estado(asentamiento.getCodigoPostal().getMunicipio().getEstado().getEstado())
                        .municipio(asentamiento.getCodigoPostal().getMunicipio().getMunicipio())
                        .codigoPostal(asentamiento.getCodigoPostal().getCp())
                        .asentamiento(asentamiento.getAsentamiento())
                        .build()).toList();
                return new ResponseEntity<>(MensajeResponse.builder().error(false).mensaje("Asentamientos encontrados: "+asentaDto.size()).object(asentaDto).build(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(MensajeResponse.builder().error(false).mensaje("No se encontraron Asentamientos").object(null).build(),HttpStatus.NOT_FOUND);
            }
        }catch (Exception exDT){
            return new ResponseEntity<>(MensajeResponse.builder().error(false).mensaje("No se encontraron asentamientos").object(null).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //METODOS XTRA QUE NO PIDIERON JIJI
    /*@PostMapping("estado")//para el metodo post solo "estado"
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create (@RequestBody EstadoDto estadoDto){//retorna un response entity para el manejo de httpstatus y los mensajes en caso de errores
        Estado estadoSave=null;     //el requestbody es para que retorne un json o algo asi
        try{
            estadoSave=estadoService.save(estadoDto);
            estadoDto=EstadoDto.builder()
                    .idEdo(estadoSave.getId())
                    .estado(estadoSave.getEstado()).build();
            //mediante el builder del DTO guardamos toda la informacion, recoordando cerrarlo con el build()
            return new ResponseEntity<>(MensajeResponse.builder().error(false)
                    .mensaje("Guardado correctamente").object(estadoDto).build(),HttpStatus.CREATED);
            //aqui retornamos el response entity con el mensajeresponse y el objeto que acabamos de buildear
        }catch (DataAccessException exDt){//aqui atrapamos el error en caso de haber
            return new ResponseEntity<>(
                    MensajeResponse.builder().error(true).mensaje(exDt.getMessage()).object(null).build(),
                    HttpStatus.METHOD_NOT_ALLOWED);//el http response que mandamos sera uno de error
        }
    }


    @PutMapping("estado")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update (@RequestBody EstadoDto estadoDto){//lo mismo, en este caso es update para el put
        Estado estadoUpdate=null;
        try{
            estadoUpdate = estadoService.save(estadoDto);//el metodo save sirve para lo mismo en este caso
            estadoDto=EstadoDto.builder().idEdo(estadoUpdate.getId()).estado(estadoUpdate.getEstado()).build();
            //buildeamos el dto
            return new ResponseEntity<>(MensajeResponse.builder()
                    .error(false)
                    .mensaje("Actualizado correctamente")
                    .object(estadoDto).build()
                    ,HttpStatus.CREATED);
            //retornamos el response entity con un mensajeresponse y un mensaje de todo correcto y el objeto que buildeamos
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder().error(true).mensaje(exDt.getMessage()).object(null).build(),
                    HttpStatus.METHOD_NOT_ALLOWED);//aqui mandamos la respuesta negativa y el error que se atrapo
        }
    }


    @DeleteMapping("estado/{id}")//en caso de querer borrar algo tenemos que especificarlo con su id
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete (@PathVariable Integer id){//aqui mandamos un path variable indicando que hay una variable en laURL
        try {
            Estado estadoDelete=estadoService.findById(id);//solamente ocupamos el findbyid
            estadoService.delete(estadoDelete);
            return new ResponseEntity<>(estadoDelete,HttpStatus.NO_CONTENT);
        }catch (DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder().error(true).mensaje(exDt.getMessage()).object(null).build(),
                    HttpStatus.METHOD_NOT_ALLOWED);//en caso de no encontrarlo manda un objeto nulo y un mensaje de error
        }
    }
*/

}
