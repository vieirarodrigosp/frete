package com.example.frete.httpClient;

import com.example.frete.dto.DisponibilidadeEstoqueResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient( name = "estoque", url = "${spring.cloud.openfeign.client.config.estoque.url}")
public interface EstoqueClient {
  @GetMapping(value = "/estoque/disponibilidade/{sku}")
  @ResponseStatus(HttpStatus.OK)
  DisponibilidadeEstoqueResponseDTO getDisponibilidade(@PathVariable String sku);
}