package com.oteasy.ot_billing.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
