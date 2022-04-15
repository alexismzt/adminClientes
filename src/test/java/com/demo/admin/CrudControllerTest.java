package com.demo.admin;

import com.demo.admin.custom.helpers.Utils;
import com.demo.admin.dbo.entities.Cliente;
import com.demo.admin.servcices.ClienteServicio;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.demo.admin.TestUtils.*;
import static com.demo.admin.custom.helpers.Utils.toDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AdministradorDeClientesApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CrudControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ClienteServicio servicio;

    Cliente cliente;

    @BeforeEach
    void setUp(){
        cliente = new Cliente();
        cliente.setNombre("Test");
        cliente.setApellidoPaterno("Test APaterno");
        cliente.setApellidoMaterno("Test AMaterno");
        cliente.setFechaNacimiento(toDate(LocalDate.of(1990,1,1)));
        cliente.setEmail("test@email.com");
        cliente.setRfc(RFC_GENERIC);
    }
    @Test
    @Order(1)
    void createCliente() throws Exception {
        mvc.perform(
                post("/cliente/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(DATA, data)
                        .content(Utils.toJson(cliente))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(JSON_STATUS).value(JSON_OK));
        Cliente clienteCreado = servicio.findByRfc(cliente.getRfc());
        assertNotNull(clienteCreado);
        assertTrue(clienteCreado.getId() > 0);
    }

    @Test
    @Order(2)
    void duplicarRFC() throws Exception{
        mvc.perform(
                        post("/cliente/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(DATA, data)
                                .content(Utils.toJson(cliente))
                )
                .andDo(print())
                .andExpect(jsonPath(JSON_STATUS).value(JSON_NOT_OK))
                .andExpect(jsonPath(JSON_MESSAGE).value("No se permite la duplicidad"));
    }

    @Test
    @Order(3)
    void getCliente() throws Exception {
        mvc.perform(
                get("/cliente/get/rfc")
                        .header(DATA, data)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("rfc",RFC_GENERIC)
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath(JSON_STATUS).value(JSON_OK));
    }

    @Test
    @Order(4)
    void getClienteNotFound() throws Exception {
        mvc.perform(
                        get("/cliente/get/rfc")
                                .header(DATA, data)
                                .accept(MediaType.APPLICATION_JSON)
                                .param("rfc","HEGA8407172LA")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ERROR).isNotEmpty())
                .andExpect(jsonPath(JSON_STATUS).value(JSON_NOT_FOUND));
    }

    @Test
    @Order(5)
    void updateCliente() throws Exception{
        String clienteNuevoNombre = "Test 2";
        Cliente clienteUpdate = servicio.findByRfc(cliente.getRfc());
        clienteUpdate.setNombre("Test 2");
        Long id = clienteUpdate.getId();
        mvc.perform(
                    put("/cliente/update/{id}", id)
                            .header(DATA,data)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Utils.toJson(clienteUpdate))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_STATUS).value(JSON_OK))
                .andExpect(jsonPath(JSON_PATH_NOMBRE).value(clienteNuevoNombre));
        clienteUpdate = servicio.findById(id);
        assertNotNull(clienteUpdate);
        assertEquals(clienteUpdate.getNombre(), clienteNuevoNombre);
    }

    @Test
    @Order(6)
    void getAllCliente() throws Exception {
        mvc.perform(
                        get("/cliente/listar")
                                .header(DATA, data)
                                .accept(MediaType.APPLICATION_JSON)
                                .param("page","0")
                                .param("size","1")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_STATUS).value(JSON_OK));
    }

    @Test
    @Order(7)
    void elimiarCliente() throws Exception{
        Cliente cliente = servicio.findByRfc(RFC_GENERIC);
        Long id = cliente.getId();
        mvc.perform(
               delete("/cliente/delete/{id}", id)
                       .header(DATA, data)
                       .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_STATUS).value(JSON_OK));
        assertFalse(servicio.existeRecurso(id));
    }

}
