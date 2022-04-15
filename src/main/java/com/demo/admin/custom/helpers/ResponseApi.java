package com.demo.admin.custom.helpers;

import com.demo.admin.custom.helpers.exceptions.RecursoNotFoundException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseApi {
    final int status;
    final Object restResult;
    final String message;
    final String error;

    private ResponseApi(int status, Object restResult, String message, String error) {
        this.status = status;
        this.restResult = restResult;
        this.message = message;
        this.error = error;
    }

    public static class Builder{
        private int status;
        private Object restResult;
        private String message;
        private String error;
        Map<String, Object> paginatedResponse;
        public Builder ok(){
            this.status = 200;
            this.message = "OK";
            return this;
        }
        public Builder withStatus(int status){
            this.status = status;
            return this;
        }
        public Builder withMessage(String msg){
            this.message = msg;
            return this;
        }
        public Builder withError(Exception ex){
            this.error = ex.getMessage();
            return this;
        }
        public Builder withPaginated(Object contenido, int paginaActual, int itemsTotales, int totalPaginas){
            paginatedResponse = new HashMap<>();
            paginatedResponse.put("items", contenido);
            paginatedResponse.put("paginaActual", paginaActual);
            paginatedResponse.put("items_en_total", itemsTotales);
            paginatedResponse.put("total_de_paginass", totalPaginas);
            return this;
        }
        public Builder notFound(){
            this.status = 404;
            this.message = "404 - NOT FOUND";
            this.error = new RecursoNotFoundException().getMessage();
            return this;
        }
        public Builder body(Object o){
            this.restResult = o;
            return this;
        }

        public ResponseApi build(){
            return new ResponseApi(status, restResult != null ? restResult: paginatedResponse , message, error);
        }
    }

    public static Builder withRecurso(Object recurso){
        if(recurso == null)
            return new Builder().notFound();
        else
            return new Builder().ok()
                    .body(recurso);
    }

    public static Builder badRequest(){
        return new Builder().withStatus(400).withMessage("Bad request");
    }

    public static Builder ok(){
        return new Builder().ok();
    }
    public static Builder notOk(){
        return new Builder().withStatus(500);
    }
    public static Builder notFound(){
        return new Builder()
                .withError(new RecursoNotFoundException())
                .notFound();
    }
    public static Builder withCode(int status, String msg){
        return new Builder()
                .withStatus(status)
                .withMessage(msg);
    }
}
