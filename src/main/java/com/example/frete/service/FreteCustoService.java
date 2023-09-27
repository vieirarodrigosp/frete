package com.example.frete.service;

import com.example.frete.dto.FreteCustoDTO;

public interface FreteCustoService {
  FreteCustoDTO getFreteCustoPorCEP(String cep);
}
