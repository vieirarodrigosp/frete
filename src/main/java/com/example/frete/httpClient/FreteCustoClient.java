package com.example.frete.httpClient;

import com.example.frete.dto.DisponibilidadeEstoqueResponseDTO;
import com.example.frete.dto.FreteCustoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient( name = "freteCusto", url = "${spring.cloud.openfeign.client.config.freteCusto.url}")
public interface FreteCustoClient {
  @GetMapping(value = "/frete-custo/{cep}")
  @ResponseStatus(HttpStatus.OK)
  FreteCustoDTO getFreteCustoPorCEP(@PathVariable String cep);
}