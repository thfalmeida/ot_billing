package com.oteasy.ot_billing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Representante implements ObjectModel {
    private int id;
    private String nome;
    private int empresa_id;
    private String nomeEmpresa;
    private String email;
    private String telefone;
    private String isActive;
}
