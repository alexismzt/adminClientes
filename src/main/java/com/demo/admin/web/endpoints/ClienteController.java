package com.demo.admin.web.endpoints;

import com.demo.admin.custom.helpers.ResponseApi;
import com.demo.admin.custom.helpers.Utils;
import com.demo.admin.custom.helpers.exceptions.RecursoDuplicadoException;
import com.demo.admin.custom.helpers.exceptions.RecursoNotFoundException;
import com.demo.admin.dbo.dto.ClienteDTO;
import com.demo.admin.dbo.entities.Cliente;
import com.demo.admin.servcices.ClienteServicio;
import com.google.common.base.Preconditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {
    @Autowired
    ClienteServicio servicio;
    @Autowired
    ModelMapper mapper;

    @GetMapping(value = "/get/{id}")
    public ResponseApi buscarPorId(
            @RequestHeader("DATA") final String Data,
            @PathVariable("id") final long id){
        if(!Utils.validarHeader(Data))
            return ResponseApi.badRequest().build();
        Cliente cliente = servicio.findById(id);
        if(cliente == null)
            return ResponseApi.notFound().build();

        return ResponseApi
                .withRecurso(mapper.map(cliente, ClienteDTO.class))
                .build();
    }

    @GetMapping(value = "/get/rfc", params = {"rfc"})
    public ResponseApi buscarPorRfc(
            @RequestHeader("DATA") final String Data,
            @RequestParam("rfc") final String rfc){
        if(!Utils.validarHeader(Data))
            return ResponseApi.badRequest().build();
        Cliente cliente = servicio.findByRfc(rfc);
        if(cliente == null)
            return ResponseApi.notFound().build();

        return ResponseApi
                .withRecurso(mapper.map(cliente, ClienteDTO.class))
                .build();
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseApi crearRecurso(
            @RequestHeader("DATA") final String Data,
            @RequestBody @NotNull final ClienteDTO recurso
    ){

        if(!Utils.validarHeader(Data))
            return ResponseApi.badRequest().build();
        if(servicio.existeRecurso(recurso.getRfc()))
            return ResponseApi.notOk()
                    .withError(new RecursoDuplicadoException())
                    .withMessage("No se permite la duplicidad").build();

        Preconditions.checkNotNull(recurso);
        final ClienteDTO cliente = mapper.map(
                servicio.create(
                        mapper.map(recurso, Cliente.class)),
                ClienteDTO.class);
        return ResponseApi.ok().body(cliente).build();
    }

    @PutMapping(value = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi actualizarRecurso(
            @RequestHeader("DATA") final String Data,
            @PathVariable("id") final Long id,
            @NotNull @RequestBody final ClienteDTO recurso){
        if(!Utils.validarHeader(Data))
            return ResponseApi.badRequest().build();
        Preconditions.checkNotNull(recurso);
        final ClienteDTO cliente = mapper.map(
                servicio.update(mapper.map(recurso, Cliente.class))
                , ClienteDTO.class);
        return ResponseApi.withRecurso(cliente)
                .build();

    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi eliminarRecurso(
            @RequestHeader("DATA") final String Data,
            @PathVariable("id") final Long id){
        if(!Utils.validarHeader(Data))
            return ResponseApi.badRequest().build();

        if(!servicio.existeRecurso(id))
            return ResponseApi.notFound().build();
        else {
            servicio.deleteById(id);
            return ResponseApi.ok().withMessage("Exito!").build();
        }
    }

    @GetMapping(value = "/listar", params = { "page", "size" })
    public ResponseApi findPaginated(@RequestParam("page") final int page, @RequestParam("size") final int size,
                                     final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<Cliente> resultPage = servicio.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new RecursoNotFoundException();
        }

        List<ClienteDTO> clienteDTOList = resultPage
                .getContent()
                .stream()
                .map( cliente -> mapper.map(cliente, ClienteDTO.class ) )
                .collect(Collectors.toList());

        return ResponseApi.ok()
                .body(clienteDTOList)
                .build();
    }
}
