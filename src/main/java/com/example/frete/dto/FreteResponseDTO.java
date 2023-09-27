package com.example.frete.dto;

import java.time.LocalDateTime;

public class FreteResponseDTO {
  private Integer prazoEntrega;
  private LocalDateTime dataEntrega;
  private Double valorFrete;
  private String mensagem;

  public FreteResponseDTO(Integer prazoEntrega, LocalDateTime dataEntrega, Double valorFrete, String mensagem) {
    this.prazoEntrega = prazoEntrega;
    this.dataEntrega = dataEntrega;
    this.valorFrete = valorFrete;
    this.mensagem = mensagem;
  }

  public Integer getPrazoEntrega() {
    return prazoEntrega;
  }

  public LocalDateTime getDataEntrega() {
    return dataEntrega;
  }

  public Double getValorFrete() {
    return valorFrete;
  }

  public String getMensagem() {
    return mensagem;
  }
}