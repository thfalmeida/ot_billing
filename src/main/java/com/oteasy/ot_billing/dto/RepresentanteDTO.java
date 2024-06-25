package com.oteasy.ot_billing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RepresentanteDTO {
    int id;
    String nome;
    int id_empresa;
    String nome_empresa;
    String email;
}
