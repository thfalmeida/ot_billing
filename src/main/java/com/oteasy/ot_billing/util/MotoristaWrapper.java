package com.oteasy.ot_billing.util;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oteasy.ot_billing.dto.MotoristaDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MotoristaWrapper {
    ArrayList<MotoristaDTO> items;

}
