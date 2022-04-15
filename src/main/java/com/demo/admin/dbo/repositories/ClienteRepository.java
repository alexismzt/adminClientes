package com.demo.admin.dbo.repositories;

import com.demo.admin.dbo.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findById(long id);
    Cliente findByRfc(String likeRfc);
}
