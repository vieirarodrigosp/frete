package com.example.frete.service.impl;

import com.example.frete.dto.*;
import com.example.frete.service.EstoqueService;
import com.example.frete.service.FreteCustoService;
import com.example.frete.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.frete.constants.CommonConstant.SKUS_DISPONIVEIS;
import static com.example.frete.constants.CommonConstant.SKUS_INDISPONIVEIS;

@Service
public class FreteServiceImpl implements FreteService {
  @Autowired
  private EstoqueService estoqueService;
  @Autowired
  private FreteCustoService freteCustoService;
  public FreteResponseDTO calcular(FreteRequestDTO freteRequestDTO){
    var disponibilidadeEstoqueResponse = estoqueService.getDisponibilidade(freteRequestDTO);
    var freteCusto = freteCustoService.getFreteCustoPorCEP(freteRequestDTO.getCep());
    return this.defineFreteResponse(disponibilidadeEstoqueResponse, freteCusto);
  }
  private FreteResponseDTO defineFreteResponse(DisponibilidadeEstoqueResponseDTO disponibilidadeEstoqueResponse, FreteCustoDTO freteCusto){
    var maiorTempoExpedicao = disponibilidadeEstoqueResponse.getDisponibilidadeEstoqueList().stream()
        .filter(Objects::nonNull)
        .filter(disponibilidadeEstoqueDTO -> disponibilidadeEstoqueDTO.getTempoExpedicao()!=null)
        .mapToInt(DisponibilidadeEstoqueDTO::getTempoExpedicao)
        .max()
        .orElse(0);
    var prazofinal = freteCusto.getPrazoEntrega() == null ? Long.valueOf(0) : Long.valueOf(freteCusto.getPrazoEntrega()) + Long.valueOf(maiorTempoExpedicao);
    var dataEntrega = LocalDateTime.now().plusDays(prazofinal);
    var skuindisponivelList =
        disponibilidadeEstoqueResponse.getSkuIndisponivelList() == null
            ? SKUS_DISPONIVEIS
            : SKUS_INDISPONIVEIS + disponibilidadeEstoqueResponse.getSkuIndisponivelList().stream().filter(Objects::nonNull).map(sku -> String.valueOf(sku)).collect(Collectors.joining(", "));
    return new FreteResponseDTO((int) prazofinal, dataEntrega, freteCusto.getValorFrete(), skuindisponivelList);
  }
}