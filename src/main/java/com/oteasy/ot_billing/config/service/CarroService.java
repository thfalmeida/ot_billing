package com.oteasy.ot_billing.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oteasy.ot_billing.dto.CarroDTO;
import com.oteasy.ot_billing.model.Carro;
import com.oteasy.ot_billing.repository.CarroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<CarroDTO> getAllCars() {
        try{
            List<CarroDTO> lista = carroRepository.findAll();
            return lista;
        }catch(Exception e){
            return null;
        }
        
    }

    public Optional<Carro> getCarById(int id) {
        return carroRepository.findById(id);
    }

    public Carro createCar(Carro car){
        return carroRepository.save(car);
    }

    public Optional<Carro> updateCar(int id, Carro carDetails) {
        Carro carro = carroRepository.updateCarro(id, carDetails);
        return Optional.ofNullable(carro);
    }

    public void deleteCar(int id) {
        carroRepository.delete(id);
    }
}