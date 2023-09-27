package com.example.frete.service;

import com.example.frete.dto.FreteRequestDTO;
import com.example.frete.dto.FreteResponseDTO;

public interface FreteService {
  FreteResponseDTO calcular(FreteRequestDTO freteRequestDTO);
}