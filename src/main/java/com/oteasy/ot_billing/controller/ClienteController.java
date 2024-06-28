package com.oteasy.ot_billing.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oteasy.ot_billing.repository.ClienteRepository;
import com.oteasy.ot_billing.dto.ClienteDTO;
import com.oteasy.ot_billing.model.Cliente;

@RestController
@RequestMapping("client")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping("list")
    public ResponseEntity<?> listCliente(){
        ArrayList<ClienteDTO> clientes;
        try{
            clientes = (ArrayList<ClienteDTO>) clienteRepository.findAll();
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ArrayList<ClienteDTO>>(clientes, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getClienteById(@PathVariable int id){
        Cliente cliente;
        try{
            cliente = clienteRepository.findById(id);
            if(cliente == null || cliente.getId() < 1)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable int id){
        try{
            clienteRepository.deleteCliente(id);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Cliente deletado com sucesso!", HttpStatus.OK);
    }

    @PostMapping("new")
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente){
        try{
            Cliente newCliente = clienteRepository.save(cliente);
            return new ResponseEntity<Cliente>(newCliente, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCliente(@PathVariable int id, @RequestBody Cliente cliente){
        try{
            Cliente updatedCliente = clienteRepository.updateCliente(id, cliente);
            return new ResponseEntity<>(updatedCliente, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
