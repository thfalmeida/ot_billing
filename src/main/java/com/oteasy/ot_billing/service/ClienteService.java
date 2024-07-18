package com.oteasy.ot_billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Cliente;
import com.oteasy.ot_billing.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ResponseEntity<?> findAllClientes(){
        try{
            return new ResponseEntity<>(clienteRepository.findAll(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findClienteById(int id){
        try{
            Cliente cliente = clienteRepository.findById(id);
                if(cliente != null)
                    return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
                else
                    return new ResponseEntity<String>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteCliente(int id) {
        try{
            Cliente cliente = clienteRepository.findById(id);
            if(cliente != null && cliente.getId() > 0){
                clienteRepository.deleteCliente(id);
                return new ResponseEntity<String>(HttpStatus.OK);
            }else
                return new ResponseEntity<String>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> createCliente(Cliente cliente){
        if(cliente == null || cliente.getNome() == null || cliente.getNome().isEmpty()) {
            return new ResponseEntity<String>("Dados inválidos para criação do cliente", HttpStatus.BAD_REQUEST);
        }
        try{
            Cliente newCliente = clienteRepository.save(cliente);
            return new ResponseEntity<Cliente>(newCliente,HttpStatus.OK);
        }catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateCliente(int id, Cliente cliente)  {
        try{
            Cliente existingCliente = clienteRepository.findById(id);
            if(existingCliente != null) {
                Cliente updatedCliente = clienteRepository.updateCliente(id, cliente);
                return new ResponseEntity<Cliente>(updatedCliente, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}