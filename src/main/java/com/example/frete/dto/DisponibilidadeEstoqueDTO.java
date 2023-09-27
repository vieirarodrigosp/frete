package com.example.frete.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DisponibilidadeEstoqueDTO {
  private Integer sku;
  private Boolean disponibilidade;
  @JsonProperty("tempo-expedicao")
  private Integer tempoExpedicao;

  public DisponibilidadeEstoqueDTO() {}

//  public DisponibilidadeEstoqueDTO(Integer sku, Boolean disponibilidade, Integer tempoExpedicao) {
//    this.sku = sku;
//    this.disponibilidade = disponibilidade;
//    this.tempoExpedicao = tempoExpedicao;
//  }

  public Integer getSku() {
    return sku;
  }

  public void setSku(Integer sku) {
    this.sku = sku;
  }

  public Boolean getDisponibilidade() {
    return disponibilidade;
  }

  public void setDisponibilidade(Boolean disponibilidade) {
    this.disponibilidade = disponibilidade;
  }

  public Integer getTempoExpedicao() {
    return tempoExpedicao;
  }

  public void setTempoExpedicao(Integer tempoExpedicao) {
    this.tempoExpedicao = tempoExpedicao;
  }
}