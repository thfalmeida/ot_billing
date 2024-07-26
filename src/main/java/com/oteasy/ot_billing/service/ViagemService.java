package com.oteasy.ot_billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.ObjectModel;
import com.oteasy.ot_billing.model.Viagem;
import com.oteasy.ot_billing.model.ViagemInfo;
import com.oteasy.ot_billing.repository.Repository;

@Service
public class ViagemService {

    @Autowired
    Repository repository;
    
    public ResponseEntity<?> findAllViagens(){
        try{
            List<ObjectModel> lista = repository.FindAll(Viagem.class);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findViagemById(int id){
        try{
            Viagem viagem = (Viagem) repository.findById(id, Viagem.class);
                if(viagem != null)
                    return new ResponseEntity<Viagem>(viagem, HttpStatus.OK);
                else
                    return new ResponseEntity<String>("Viagem não encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findViagemInfoById(int id){
        try{
            ViagemInfo viagem = (ViagemInfo) repository.findById(id, ViagemInfo.class);
                if(viagem != null)
                    return new ResponseEntity<ViagemInfo>(viagem, HttpStatus.OK);
                else
                    return new ResponseEntity<String>("Viagem não encontrada", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findAllViagensInfo(){
        try{
            List<ObjectModel> lista = repository.FindAll(ViagemInfo.class);
            return new ResponseEntity<List<ObjectModel>>(lista, HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteViagem(int id) {
        try{
            Viagem viagem = (Viagem) repository.findById(id, Viagem.class);
            if(viagem != null && viagem.getId() > 0){
                repository.delete(id, Viagem.class);
                return new ResponseEntity<String>(HttpStatus.OK);
            }else
                return new ResponseEntity<String>("Viagem não encontrado", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> createViagem(Viagem viagem){
        try{
            Viagem newViagem = (Viagem) repository.save(viagem, Viagem.class);
            return new ResponseEntity<Viagem>(newViagem,HttpStatus.OK);
        }catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateViagem(int id, Viagem viagem)  {
        try{
            Viagem existingViagem = (Viagem) repository.findById(id, Viagem.class);
            if(existingViagem != null && existingViagem.getId() > 0) {
                Viagem updatedViagem = (Viagem) repository.save(id, viagem, Viagem.class);
                return new ResponseEntity<Viagem>(updatedViagem, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Viagem não encontrado", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
