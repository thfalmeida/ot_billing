package com.oteasy.ot_billing.controller;

import java.util.ArrayList;
import java.util.Optional;

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

import com.oteasy.ot_billing.config.service.ClienteService;
import com.oteasy.ot_billing.model.Cliente;

@RestController
@RequestMapping("client")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("list")
    public ResponseEntity<?> listCliente(){
        ArrayList<Cliente> clientes;
        try{
            clientes = (ArrayList<Cliente>) clienteService.findAllClientes();
            return new ResponseEntity<ArrayList<Cliente>>(clientes, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getClienteById(@PathVariable int id){
        try {
            Optional<Cliente> cliente = clienteService.findClienteById(id);
            return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable int id){
        try{
            clienteService.deleteCliente(id);
            return new ResponseEntity<String>("Cliente deletado com sucesso!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente){
        try{
            Cliente newCliente = clienteService.createCliente(cliente);
            return new ResponseEntity<Cliente>(newCliente, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCliente(@PathVariable int id, @RequestBody Cliente cliente){
        try{
            Cliente updatedCliente = clienteService.updateCliente(id, cliente);
            return new ResponseEntity<>(updatedCliente, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
