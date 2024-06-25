package com.oteasy.ot_billing.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Representante {
    private int id;
    @NonNull
    private String nome;
    private int empresa_id;
    @NonNull
    private String email;
    @NonNull
    private String telefone;
    private String isActive;
}
