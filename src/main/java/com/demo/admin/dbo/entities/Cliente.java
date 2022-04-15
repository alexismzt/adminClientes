package com.demo.admin.dbo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
    @Version
    private long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    String rfc;
    @Column(nullable = false)
    String nombre;
    @Column(nullable = false)
    String apellidoPaterno;
    @Column(nullable = false)
    String apellidoMaterno;
    @Column(nullable = false)
    Date fechaNacimiento;
    @Column
    String numeroCelular;
    @Column
    @Email
    String email;
}
