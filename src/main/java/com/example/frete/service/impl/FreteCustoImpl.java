package com.example.frete.service.impl;

import com.example.frete.dto.FreteCustoDTO;
import com.example.frete.exception.DisponibilidadeEstoqueException;
import com.example.frete.helpers.ObjectHelper;
import com.example.frete.httpClient.FreteCustoClient;
import com.example.frete.service.FreteCustoService;
import feign.FeignException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.frete.constants.CommonConstant.CEP_NAO_ENCONTRADA;
import static com.example.frete.constants.CommonConstant.CONTRATO_MOCK_CEP;

@Service
public class FreteCustoImpl implements FreteCustoService {
  @Autowired
  private FreteCustoClient freteCustoClient;
  @Bulkhead(name = "getFreteCustoPorCEP",fallbackMethod = "getFreteCustoPorCEPFallbackMethod")
  public FreteCustoDTO getFreteCustoPorCEP(String cep){
    return freteCustoClient.getFreteCustoPorCEP(cep);
  }
  public FreteCustoDTO getFreteCustoPorCEPFallbackMethod(String cep, FeignException.FeignClientException exception) throws IOException {
    if(exception.status() == 409)
      throw new DisponibilidadeEstoqueException(CEP_NAO_ENCONTRADA + cep);
    var payload = CONTRATO_MOCK_CEP;
    return ObjectHelper.convertStringTo(payload, FreteCustoDTO.class);
  }
}