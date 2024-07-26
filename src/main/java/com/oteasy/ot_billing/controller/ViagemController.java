package com.oteasy.ot_billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oteasy.ot_billing.service.ViagemService;
import com.oteasy.ot_billing.model.Viagem;

@Controller
@RequestMapping("trip")
public class ViagemController {

    @Autowired
    ViagemService viagemService;

    @GetMapping("{id}")
    public ResponseEntity<?> getViagemById(@PathVariable int id){
        return viagemService.findViagemById(id);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getViagemInfoById(@PathVariable int id){
        return viagemService.findViagemInfoById(id);
    }


    @GetMapping("list")
    public ResponseEntity<?> listViagem(){
        return viagemService.findAllViagens();
    }

    @GetMapping("/info/list")
    public ResponseEntity<?> listInfoViagem(){
        return viagemService.findAllViagensInfo();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteViagem(@PathVariable int id){
        return viagemService.deleteViagem(id);
    }

    @PostMapping("new")
    public ResponseEntity<?> createViagem(@RequestBody Viagem viagem){
        return viagemService.createViagem(viagem);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateViagem(@PathVariable int id, @RequestBody Viagem viagem){
        return viagemService.updateViagem(id, viagem);
    }
}
