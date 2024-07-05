package com.oteasy.ot_billing.config.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Viagem;
import com.oteasy.ot_billing.repository.ViagemRepository;

@Service
public class ViagemService {

    @Autowired
    ViagemRepository viagemRepository;

    public Optional<Viagem> findViagemById(int id) throws Exception{
        Optional<Viagem> viagem = Optional.ofNullable(viagemRepository.findById(id));
        return viagem;
    }
    
    public List<Viagem> findAllViagens() throws Exception{
        return viagemRepository.findAll();
    }

    public void deleteViagem(int id) throws Exception{
        Optional<Viagem> viagem = findViagemById(id);
        System.out.println((viagem.isPresent()));
        if(viagem.isPresent())
            viagemRepository.delete(id);
        else
            throw new Exception("Viagem não encontrado");
    }

    public Viagem createViagem(Viagem viagem) throws Exception{
        return viagemRepository.save(viagem);
    }

    public Viagem updateViagem(int id, Viagem viagem) throws Exception{
        Optional<Viagem> existingViagem = findViagemById(id);
        if(existingViagem.isPresent())
            return viagemRepository.update(id, existingViagem.get());
        else
            throw new Exception("Viagem não encontrado");
    }
    
}
