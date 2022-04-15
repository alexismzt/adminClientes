package com.demo.admin;

import com.demo.admin.custom.helpers.Utils;
import com.demo.admin.custom.helpers.exceptions.RecursoNotFoundException;
import com.demo.admin.dbo.entities.Cliente;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class UtilsTests {

    @Test
    void rfcValidoTest(){
       assertTrue(Utils.esRFCValido(TestUtils.RFC_GENERIC));
    }
    @Test
    void rfcInvladido(){
        assertFalse(Utils.esRFCValido("XXXX99BB9927u"));
    }
    @Test
    void esHeaderValido(){
        assertTrue(Utils.validarHeader(TestUtils.data));
    }
    @Test
    void esHeaderInvalido(){
        assertFalse(Utils.validarHeader("CadenaInválida"));
    }
    @Test
    void convertirFecha(){
        Date dt = Utils.toDate(LocalDate.now());
        Date dt2 = new Date();
        assertTrue((dt.getTime() - dt2.getTime()) < 1000);
    }
    @Test
    void checkRecursoValido(){
        assertNotNull(Utils.checkRecurso(new Cliente()));
    }
    @Test
    void checkRecursoInvalido(){
        assertThrowsExactly(RecursoNotFoundException.class, () -> Utils.checkRecurso(null));
    }
}
