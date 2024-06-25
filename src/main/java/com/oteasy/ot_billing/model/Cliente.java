package com.oteasy.ot_billing.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Cliente {
    private int id;
    @NonNull
    private String nome;
    private String endereco;
    private String tipo;
}
