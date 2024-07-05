package com.oteasy.ot_billing.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oteasy.ot_billing.dto.CarroDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarroWrapper {
    List<CarroDTO> items;

}
