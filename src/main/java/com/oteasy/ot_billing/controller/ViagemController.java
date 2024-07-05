package com.oteasy.ot_billing.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oteasy.ot_billing.config.service.ViagemService;
import com.oteasy.ot_billing.model.Viagem;

@Controller
@RequestMapping("trip")
public class ViagemController {

    @Autowired
    ViagemService viagemService;

        @GetMapping("{id}")
        public ResponseEntity<?> getViagemById(@PathVariable int id){
        try {
            Optional<Viagem> viagem = viagemService.findViagemById(id);
            return viagem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("list")
    public ResponseEntity<?> listViagem(){
        try{
            List<Viagem> viagens;
            viagens = viagemService.findAllViagens();
            return new ResponseEntity<List<Viagem>>(viagens, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteViagem(@PathVariable int id){
        try{
            viagemService.deleteViagem(id);
            return new ResponseEntity<String>("Viagem deletada com sucesso!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("new")
    public ResponseEntity<?> createViagem(@RequestBody Viagem viagem){
        try{
            Viagem newViagem = viagemService.createViagem(viagem);
            return new ResponseEntity<Viagem>(newViagem, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateViagem(@PathVariable int id, @RequestBody Viagem viagem){
        try{
            Viagem updatedViagem = viagemService.updateViagem(id, viagem);
            return new ResponseEntity<>(updatedViagem, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
