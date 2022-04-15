package com.demo.admin.servcices;

import com.demo.admin.dbo.entities.Cliente;
import com.demo.admin.dbo.repositories.ClienteRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ClienteServicioImpl extends ServiciosAbstractLayer<Cliente> implements ClienteServicio{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    protected PagingAndSortingRepository<Cliente, Long> getDao() {
        return clienteRepository;
    }

    @Override
    public Page<Cliente> findPaginated(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Override
    public Cliente findByRfc(String rfc) {
        return clienteRepository.findByRfc(rfc);
    }

    @Override
    public boolean existeRecurso(Long id) {
        return clienteRepository.findById(id).isPresent();
    }
    @Override
    public boolean existeRecurso(String rfc) {
        return clienteRepository.findByRfc(rfc) != null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }
}
