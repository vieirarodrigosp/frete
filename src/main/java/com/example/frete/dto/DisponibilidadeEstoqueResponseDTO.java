package com.example.frete.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DisponibilidadeEstoqueResponseDTO {
  @JsonProperty("disponibilidade-estoque")
  private List<DisponibilidadeEstoqueDTO> disponibilidadeEstoqueList;
  @JsonProperty("tempo-resposta")
  private double tempoResposta;
  private String mensagem;
  private List<Integer> skuIndisponivelList;

  public DisponibilidadeEstoqueResponseDTO() {}

//  public DisponibilidadeEstoqueResponseDTO(List<DisponibilidadeEstoqueDTO> disponibilidadeEstoqueList, double tempoResposta, String mensagem, List<Integer> skuIndisponivelList) {
//    this.disponibilidadeEstoqueList = disponibilidadeEstoqueList;
//    this.tempoResposta = tempoResposta;
//    this.mensagem = mensagem;
//    this.skuIndisponivelList = skuIndisponivelList;
//  }

  public List<DisponibilidadeEstoqueDTO> getDisponibilidadeEstoqueList() {
    return disponibilidadeEstoqueList;
  }

  public void setDisponibilidadeEstoqueList(List<DisponibilidadeEstoqueDTO> disponibilidadeEstoqueList) {
    this.disponibilidadeEstoqueList = disponibilidadeEstoqueList;
  }

  public void setTempoResposta(double tempoResposta) {
    this.tempoResposta = tempoResposta;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public List<Integer> getSkuIndisponivelList() {
    return skuIndisponivelList;
  }

  public void setSkuIndisponivelList(List<Integer> skuIndisponivelList) {
    this.skuIndisponivelList = skuIndisponivelList;
  }
}