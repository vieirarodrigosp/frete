package com.example.frete.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FreteCustoDTO {
  private String transportadora;
  @JsonProperty("valor-frete")
  private double valorFrete;
  @JsonProperty("prazo-entrega")
  private Integer prazoEntrega;
  private String cep;
  @JsonProperty("http_status")
  private Integer httpStatus;
  private String mensagem;

  public FreteCustoDTO() { }

  public FreteCustoDTO(String transportadora, double valorFrete, Integer prazoEntrega, String cep, Integer httpStatus, String mensagem) {
    this.transportadora = transportadora;
    this.valorFrete = valorFrete;
    this.prazoEntrega = prazoEntrega;
    this.cep = cep;
    this.httpStatus = httpStatus;
    this.mensagem = mensagem;
  }

  public String getTransportadora() {
    return transportadora;
  }

  public void setTransportadora(String transportadora) {
    this.transportadora = transportadora;
  }

  public double getValorFrete() {
    return valorFrete;
  }

  public void setValorFrete(double valorFrete) {
    this.valorFrete = valorFrete;
  }

  public Integer getPrazoEntrega() {
    return prazoEntrega;
  }

  public void setPrazoEntrega(Integer prazoEntrega) {
    this.prazoEntrega = prazoEntrega;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public Integer getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(Integer httpStatus) {
    this.httpStatus = httpStatus;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }
}