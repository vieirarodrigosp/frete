package com.example.frete.service;

import com.example.frete.dto.DisponibilidadeEstoqueResponseDTO;
import com.example.frete.dto.FreteRequestDTO;

public interface EstoqueService {
  DisponibilidadeEstoqueResponseDTO getDisponibilidade(FreteRequestDTO freteRequestDTO);
}