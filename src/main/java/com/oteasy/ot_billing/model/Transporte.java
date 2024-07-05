package com.oteasy.ot_billing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Transporte {
    private int id;
    private int viagem_id;
    private String numTransporte;
    private String destino;

    public Transporte(int viagem_id, String numTransporte, String destino){
        this.viagem_id = viagem_id;
        this.numTransporte = numTransporte;
        this.destino = destino;
    }

}
