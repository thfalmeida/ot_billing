package com.oteasy.ot_billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> getAllCars() {
        try{
            List<CarroDTO> lista = carroRepository.findAll();
            return new ResponseEntity<List<CarroDTO>>(lista, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    public ResponseEntity<?> getCarById(int id) {
        Optional<Carro> carro = carroRepository.findById(id);
        if (carro.isPresent()) {
            return ResponseEntity.ok(carro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Carro> createCar(Carro car){
        return ResponseEntity.ok(carroRepository.save(car)); 
    }

    public ResponseEntity<Carro> updateCar(int id, Carro carDetails) {
        Carro carro = carroRepository.updateCarro(id, carDetails);
        if (carro != null) {
            return new ResponseEntity<Carro>(carro, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deleteCar(int id) {
        try{
            carroRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}