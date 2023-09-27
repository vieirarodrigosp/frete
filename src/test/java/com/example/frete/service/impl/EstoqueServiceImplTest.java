package com.example.frete.service.impl;

import com.example.frete.dto.DisponibilidadeEstoqueResponseDTO;
import com.example.frete.dto.FreteRequestDTO;
import com.example.frete.exception.DisponibilidadeEstoqueException;
import com.example.frete.helpers.ObjectHelper;
import com.example.frete.httpClient.EstoqueClient;
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

import static com.example.frete.constants.CommonConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EstoqueServiceImplTest {
  @InjectMocks
  private EstoqueServiceImpl estoqueServiceImpl;
  @Mock
  private EstoqueClient estoqueClient;
  @Test
  void getDisponibilidade_retornaDisponibilidade() throws IOException {
    var payload = "{\"skuList\":[10000,54000,546446,65000,25000],\"cep\":\"04654-654\"}";
    var freteRequestDTO = ObjectHelper.convertStringTo(payload, FreteRequestDTO.class);
    var payloadDisponibilidade = CONTRATO_DISPONIBILIDADE_ESTOQUE;
    var disponibilidadeEstoqueResponse = ObjectHelper.convertStringTo(payloadDisponibilidade, DisponibilidadeEstoqueResponseDTO.class);

    when(estoqueClient.getDisponibilidade(anyString())).thenReturn(disponibilidadeEstoqueResponse);

    var actual = estoqueServiceImpl.getDisponibilidade(freteRequestDTO);

    assertEquals(actual.getDisponibilidadeEstoqueList().get(0).getSku(), freteRequestDTO.getSkuList().get(0));
    assertNull(actual.getMensagem());
  }

  @Test
  void getDisponibilidadeFallbackMethod_erro404NOtFound_DisponibilidadeEstoqueException() throws IOException {
    var payload = "{\"skuList\":[10000,54000,546446,65000,25000],\"cep\":\"04654-654\"}";
    var freteRequestDTO = ObjectHelper.convertStringTo(payload, FreteRequestDTO.class);

    Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
    FeignException.FeignClientException exception = new FeignException.Conflict("Bad Request", request, null, null);

    var thown = assertThrows(DisponibilidadeEstoqueException.class
        , () -> estoqueServiceImpl.getDisponibilidadeFallbackMethod(freteRequestDTO, exception));
    assertTrue(thown.getMessage().contains(DISPONIBILIDADE_ESTOQUE_NAO_ENCONTRADA));
  }

  @Test
  void getDisponibilidadeFallbackMethod_erroDiferente404RetornaValorDefault() throws IOException {
    var payload = "{\"skuList\":[10000,54000,546446,65000,25000],\"cep\":\"04654-654\"}";
    var freteRequestDTO = ObjectHelper.convertStringTo(payload, FreteRequestDTO.class);

    Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), null, new RequestTemplate());
    FeignException.FeignClientException exception = new FeignException.BadRequest("Bad Request", request, null, null);

    var actual = estoqueServiceImpl.getDisponibilidadeFallbackMethod(freteRequestDTO, exception);

    assertEquals(actual.getDisponibilidadeEstoqueList().get(0).getSku(), freteRequestDTO.getSkuList().get(0));
    assertNull(actual.getMensagem());
  }
}