package com.oteasy.ot_billing.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViagemInfo implements ObjectModel {
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dataRegistro;
    private long km_inicial;
    private long km_final;
    private String motorista;
    private String carro;
    private String cliente;
    private float valor;
    private int qnt_transporte;
    private String observacao;
    private int num_ajudante;
}
