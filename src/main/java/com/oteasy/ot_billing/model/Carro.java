package com.oteasy.ot_billing.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Carro {
    private int id;
    private String nome;
    @NonNull
    private String placa;

}
