package com.example.frete.service.impl;

import com.example.frete.dto.DisponibilidadeEstoqueDTO;
import com.example.frete.dto.DisponibilidadeEstoqueResponseDTO;
import com.example.frete.dto.FreteRequestDTO;
import com.example.frete.exception.DisponibilidadeEstoqueException;
import com.example.frete.helpers.ObjectHelper;
import com.example.frete.httpClient.EstoqueClient;
import com.example.frete.service.EstoqueService;
import feign.FeignException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.frete.constants.CommonConstant.CONTRATO_DISPONIBILIDADE_ESTOQUE;
import static com.example.frete.constants.CommonConstant.DISPONIBILIDADE_ESTOQUE_NAO_ENCONTRADA;

@Service
public class EstoqueServiceImpl implements EstoqueService {
  @Autowired
  private EstoqueClient estoqueClient;
  @Bulkhead(name = "getDisponibilidade",fallbackMethod = "getDisponibilidadeFallbackMethod")
  public DisponibilidadeEstoqueResponseDTO getDisponibilidade(FreteRequestDTO freteRequestDTO){
    var disponibilidadeEstoque = estoqueClient.getDisponibilidade(freteRequestDTO.getSkuToString());
    this.editaDisponibilidadeEstoqueResponseDTO(disponibilidadeEstoque, freteRequestDTO.getSkuList());
    return disponibilidadeEstoque;
  }
  public DisponibilidadeEstoqueResponseDTO getDisponibilidadeFallbackMethod(FreteRequestDTO freteRequestDTO, FeignException.FeignClientException exception) throws IOException {
    if(exception.status() == 409)
      throw new DisponibilidadeEstoqueException(DISPONIBILIDADE_ESTOQUE_NAO_ENCONTRADA + freteRequestDTO.getSkuToString());
    var payload = CONTRATO_DISPONIBILIDADE_ESTOQUE;
    var disponibilidadeEstoque = ObjectHelper.convertStringTo(payload, DisponibilidadeEstoqueResponseDTO.class);
    this.editaDisponibilidadeEstoqueResponseDTO(disponibilidadeEstoque, freteRequestDTO.getSkuList());
    return disponibilidadeEstoque;
  }
  private void editaDisponibilidadeEstoqueResponseDTO(DisponibilidadeEstoqueResponseDTO disponibilidadeEstoque, List<Integer> skuListRequest){
    this.setSkuIndisponivelList(disponibilidadeEstoque, skuListRequest);
    disponibilidadeEstoque.getDisponibilidadeEstoqueList().removeIf(disponibilidadeEstoqueDTO -> !disponibilidadeEstoqueDTO.getDisponibilidade());
  }
  private void setSkuIndisponivelList(DisponibilidadeEstoqueResponseDTO disponibilidadeEstoque, List<Integer> skuListRequest){
    var skuDisponiveis = disponibilidadeEstoque.getDisponibilidadeEstoqueList().stream()
        .filter(Objects::nonNull)
        .filter(disponibilidadeEstoqueDTO -> disponibilidadeEstoqueDTO.getDisponibilidade())
        .map(DisponibilidadeEstoqueDTO::getSku).collect(Collectors.toList());

    Set<Integer> similar = new HashSet<>(skuDisponiveis);
    Set<Integer> diferente = new HashSet<>();

    diferente.addAll(disponibilidadeEstoque.getDisponibilidadeEstoqueList().stream().map(DisponibilidadeEstoqueDTO::getSku).collect(Collectors.toList()));
    diferente.addAll(skuListRequest);

    diferente.removeAll(similar);

    disponibilidadeEstoque.setSkuIndisponivelList(diferente.stream().collect(Collectors.toList()));
  }
}