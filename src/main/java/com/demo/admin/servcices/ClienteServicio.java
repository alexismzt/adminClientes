package com.demo.admin.servcices;

import com.demo.admin.dbo.entities.Cliente;
import com.demo.admin.dbo.repositories.CommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteServicio extends CommonRepository<Cliente> {
    Page<Cliente> findPaginated(Pageable pageable);
    Cliente findByRfc(String rfc);
    boolean existeRecurso(Long id);
    boolean existeRecurso(String rfc);

}
