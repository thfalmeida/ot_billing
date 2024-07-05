package com.oteasy.ot_billing.config.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.model.Motorista;
import com.oteasy.ot_billing.repository.MorotistaRepository;

@Service
public class MotoristaService {

    @Autowired
    MorotistaRepository motoristaRepository;

    public List<Motorista> findAllMotoristas() throws Exception{
        return motoristaRepository.listAll();
    }

    public Optional<Motorista> findMotoristaById(int id) throws IOException, InterruptedException{
        return motoristaRepository.findById(id);
    }

    public void deleteMotorista(int id) throws Exception {
        Optional<Motorista> cliente = findMotoristaById(id);
        if(cliente.isPresent()) {
            motoristaRepository.deleteMotorista(id);
        } else {
            throw new Exception("Motorista não encontrado");
        }
    }

    public Motorista createMotorista(Motorista motorista) throws Exception {
        if(motorista == null || motorista.getNome() == null || motorista.getTelefone() == null) {
            throw new Exception("Dados inválidos para criação do motorista");
        }
        return motoristaRepository.save(motorista);
    }

    public Motorista updateMotorista(int id, Motorista motorista) throws Exception {
        Optional<Motorista> existingMotorista = findMotoristaById(id);
        if(existingMotorista.isPresent()) {
            return motoristaRepository.updateMotorista(id, motorista);
        } else {
            throw new Exception("Motorista não encontrado");
        }
    }
}
