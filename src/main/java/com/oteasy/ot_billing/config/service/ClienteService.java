package com.oteasy.ot_billing.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Cliente;
import com.oteasy.ot_billing.repository.ClienteRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAllClientes() throws IOException, InterruptedException {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findClienteById(int id) throws IOException, InterruptedException {
        return Optional.ofNullable(clienteRepository.findById(id));
    }

    public void deleteCliente(int id) throws Exception {
        Optional<Cliente> cliente = findClienteById(id);
        if(cliente.isPresent()) {
            clienteRepository.deleteCliente(id);
        } else {
            throw new Exception("Cliente não encontrado");
        }
    }

    public Cliente createCliente(Cliente cliente) throws Exception {
        if(cliente == null || cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new Exception("Dados inválidos para criação do cliente");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(int id, Cliente cliente) throws Exception {
        Optional<Cliente> existingCliente = findClienteById(id);
        if(existingCliente.isPresent()) {
            return clienteRepository.updateCliente(id, cliente);
        } else {
            throw new Exception("Cliente não encontrado");
        }
    }
}