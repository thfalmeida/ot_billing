package com.oteasy.ot_billing.config.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Representante;
import com.oteasy.ot_billing.repository.RepresentanteRepository;

@Service
public class RepresentanteService {
    
    @Autowired
    RepresentanteRepository representanteRepository;

    public Optional<Representante> findRepresentanteById(int id) throws Exception{
        return Optional.ofNullable(representanteRepository.findById(id));
    }

    public List<Representante> findAllRepresentantes() throws Exception {
        return representanteRepository.findAll();
    }

    public void deleteRepresentante(int id) throws Exception{
        Optional<Representante> representante = findRepresentanteById(id);
        if(representante.isPresent())
            representanteRepository.delete(id);
        else
            throw new Exception("Representante não encontrado");
    }

    public Representante updateRepresentante(int id, Representante representante) throws Exception{
        Optional<Representante> existingRepresentante = findRepresentanteById(id);
        if(existingRepresentante.isPresent()) {
            return representanteRepository.update(id, representante);
        } else {
            throw new Exception("Cliente não encontrado");
        }
    }

    public Representante createRepresentante(Representante representante) throws Exception {
        if(representante == null || representante.getNome() == null || representante.getNome().isEmpty()) {
            throw new Exception("Dados inválidos para criação do cliente");
        }
        return representanteRepository.save(representante);
    }

}
