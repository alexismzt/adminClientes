package com.demo.admin.dbo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class ClienteDTO implements Serializable {
    Long id;
    String rfc;
    String nombre;
    String apellidoPaterno;
    String apellidoMaterno;
    Date fechaNacimiento;
    String numeroCelular;
    String email;
}

