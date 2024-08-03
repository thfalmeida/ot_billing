package com.oteasy.ot_billing.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Viagem implements ObjectModel {
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dataRegistro;
    private long km_inicial;
    private long km_final;
    private int motorista_id;
    private int carro_id;
    private float valor;
    private int cliente_id;
    private String observacao;
    private int num_ajudante;
}
