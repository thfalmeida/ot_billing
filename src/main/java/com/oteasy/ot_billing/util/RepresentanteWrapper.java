package com.oteasy.ot_billing.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import com.oteasy.ot_billing.dto.RepresentanteDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepresentanteWrapper {
    List<RepresentanteDTO> items;
}
