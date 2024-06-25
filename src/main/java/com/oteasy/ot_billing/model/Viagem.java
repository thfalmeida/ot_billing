package com.oteasy.ot_billing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Viagem {
    private int id;
    private long data;
    private long km_inicial;
    private long km_final;
    private int motorista_id;
    private int carro_id;
    private float valor;
    private int cliente_id;
    private String observacao;
    private int num_ajudante;

}
