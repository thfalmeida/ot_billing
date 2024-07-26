package com.oteasy.ot_billing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViagemInfo implements ObjectModel {
    public int id;
    public long data;
    public long km_inicial;
    public long km_final;
    public String motorista;
    public String carro;
    public String cliente;
    public int qnt_transporte;
    public String observacao;
    public int num_ajudante;

    
}
