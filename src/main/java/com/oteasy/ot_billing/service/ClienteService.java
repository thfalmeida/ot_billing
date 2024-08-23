package com.oteasy.ot_billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Cliente;
import com.oteasy.ot_billing.model.ObjectModel;
import com.oteasy.ot_billing.repository.Repository;

@Service
public class ClienteService {

    @Autowired
    Repository repository;


    public ResponseEntity<?> findAllClientes(){
        try{
            List<ObjectModel> lista = repository.FindAll(Cliente.class);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findClienteById(int id){
        try{
            Cliente cliente =  (Cliente) repository.findById(id, Cliente.class);
                if(cliente != null)
                    return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
                else
                    return new ResponseEntity<String>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteCliente(int id) {
        try{
            Cliente cliente = (Cliente) repository.findById(id, Cliente.class);
            if(cliente != null && cliente.getId() > 0){
                repository.delete(id, Cliente.class);
                return new ResponseEntity<String>(HttpStatus.OK);
            }else
                return new ResponseEntity<String>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> createCliente(Cliente cliente){
        try{
            Cliente newCliente = (Cliente) repository.save(cliente, Cliente.class);
            return new ResponseEntity<Cliente>(newCliente,HttpStatus.OK);
        }catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateCliente(int id, Cliente cliente)  {
        try{
            System.out.println("Updanting Cliente");
            Cliente existingCliente = (Cliente) repository.findById(id, Cliente.class);
            System.out.println("Cliente: " + existingCliente.getId());
            if(existingCliente != null && existingCliente.getId() > 0) {
                Cliente updatedCliente = (Cliente)repository.save(id, cliente, Cliente.class);
                return new ResponseEntity<Cliente>(updatedCliente, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Cliente não encontrado", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}