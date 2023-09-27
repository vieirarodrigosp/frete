package com.example.frete.service.impl;

import com.example.frete.dto.FreteCustoDTO;
import com.example.frete.exception.DisponibilidadeEstoqueException;
import com.example.frete.httpClient.FreteCustoClient;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.HashMap;

import static com.example.frete.constants.CommonConstant.CEP_NAO_ENCONTRADA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FreteCustoImplTest {
  @InjectMocks
  private FreteCustoImpl freteCustoImpl;
  @Mock
  private FreteCustoClient freteCustoClient;
  @Test
  void getFreteCustoPorCEP_retornaTransportadora() {
    var freteCustoDTO = new FreteCustoDTO("transportadora", 12.99, 7, "00000-000", 200, "mensagem");
    when(freteCustoClient.getFreteCustoPorCEP(anyString())).thenReturn(freteCustoDTO);
    var actual = freteCustoImpl.getFreteCustoPorCEP("00000-000");
    assertEquals(actual.getTransportadora(), freteCustoDTO.getTransportadora());
    assertEquals(actual.getValorFrete(), freteCustoDTO.getValorFrete());
    assertEquals(actual.getCep(), freteCustoDTO.getCep());
    assertEquals(actual.getHttpStatus(), freteCustoDTO.getHttpStatus());
    assertEquals(actual.getMensagem(), freteCustoDTO.getMensagem());
  }

  @Test
  void getFreteCustoPorCEPFallbackMethod_QuandoOErroE404NotFound() throws IOException {
    Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
    FeignException.FeignClientException exception = new FeignException.Conflict("Bad Request", request, null, null);

    var thown = assertThrows(DisponibilidadeEstoqueException.class
        , () -> freteCustoImpl.getFreteCustoPorCEPFallbackMethod("00000-000", exception));
    assertTrue(thown.getMessage().contains(CEP_NAO_ENCONTRADA));
  }
  @Test
  void getFreteCustoPorCEPFallbackMethod_QuandoOErroForDiferenteDe404() throws IOException {
    Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
    FeignException.FeignClientException exception = new FeignException.BadRequest("Bad Request", request, null, null);
    var expected = new FreteCustoDTO("ASAPLOG", 43.5, 7, "20771-000", 200, null);
    var actual = freteCustoImpl.getFreteCustoPorCEPFallbackMethod("20771-000", exception);
    assertEquals(actual.getTransportadora(), expected.getTransportadora());
    assertEquals(actual.getValorFrete(), expected.getValorFrete());
    assertEquals(actual.getCep(), expected.getCep());
    assertEquals(actual.getHttpStatus(), expected.getHttpStatus());
    assertEquals(actual.getMensagem(), expected.getMensagem());
  }
}