package com.oteasy.ot_billing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ViagemDTO {
    private int id;
    private long data;
    private String nome_motorista;
    private String nome_carro;
    private String nome_cliente;
    private float valor;
    private String observacao;
    private int num_ajudante;
}
