package com.oteasy.ot_billing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TransporteDTO {
    int id;
    int viagem_id;
    String destino;
    String num_Transporte;
}
