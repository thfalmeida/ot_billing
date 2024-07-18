package com.oteasy.ot_billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oteasy.ot_billing.service.ClienteService;
import com.oteasy.ot_billing.model.Cliente;

@RestController
@RequestMapping("client")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("list")
    public ResponseEntity<?> listClientes(){
        return clienteService.findAllClientes();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getClienteById(@PathVariable int id){
        return clienteService.findClienteById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable int id){
        return clienteService.deleteCliente(id);
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente){
        return clienteService.createCliente(cliente);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCliente(@PathVariable int id, @RequestBody Cliente cliente){
        return clienteService.updateCliente(id, cliente);
    }
    
}
