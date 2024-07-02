package com.oteasy.ot_billing.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.oteasy.ot_billing.dto.ViagemDTO;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViagemWrapper {
    List<ViagemDTO> items;
}
