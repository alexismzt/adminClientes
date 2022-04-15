package com.demo.admin.custom.helpers;

import com.demo.admin.custom.helpers.exceptions.RecursoNotFoundException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

@Slf4j
public class Utils {

    private Utils(){

    }

    public static final String RFC_PATTERN = "^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))([A-Z\\d]{3})?$";
    public static boolean esRFCValido(String rfc){
        rfc = rfc.replaceAll("[-_ ]", "").toUpperCase();
        if (rfc.length() < 12 || rfc.length() > 13)
            return false;
        return Pattern.compile(RFC_PATTERN).matcher(rfc).matches();
    }


    public static <T> T checkRecurso(final T recurso){
        if(recurso == null){
            log.warn("No se encontro el recurso especificado");
            throw new RecursoNotFoundException();
        }
        return recurso;
    }

    public static boolean validarHeader(String headerValue){
        String shaValue = Hashing.sha256().hashString("ACLARACIONES", StandardCharsets.UTF_8)
                .toString();
        return shaValue.equals(headerValue);
    }

    public static Date toDate(LocalDate date){
        return Date.from(date.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
