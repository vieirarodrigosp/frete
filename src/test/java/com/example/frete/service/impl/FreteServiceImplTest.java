package com.example.frete.service.impl;

import com.example.frete.dto.DisponibilidadeEstoqueResponseDTO;
import com.example.frete.dto.FreteCustoDTO;
import com.example.frete.dto.FreteRequestDTO;
import com.example.frete.helpers.ObjectHelper;
import com.example.frete.service.EstoqueService;
import com.example.frete.service.FreteCustoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static com.example.frete.constants.CommonConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FreteServiceImplTest {
  @InjectMocks
  private FreteServiceImpl freteServiceImpl;
  @Mock
  private EstoqueService estoqueService;
  @Mock
  private FreteCustoService freteCustoService;
  @Test
  void calcular_geraroCalculoPeloServicodoEstoqueEPeloServicodoTransportadora() throws IOException {
    var payload = "{\"skuList\":[10000,54000,546446,65000,25000],\"cep\":\"04654-654\"}";
    var freteRequestDTO = ObjectHelper.convertStringTo(payload, FreteRequestDTO.class);
    var payloadDisponibilidade = CONTRATO_DISPONIBILIDADE_ESTOQUE;
    var disponibilidadeEstoqueResponse = ObjectHelper.convertStringTo(payloadDisponibilidade, DisponibilidadeEstoqueResponseDTO.class);
    var payloadFreteCusto = CONTRATO_MOCK_CEP;
    var freteCustoDTO = ObjectHelper.convertStringTo(payloadFreteCusto, FreteCustoDTO.class);

    when(estoqueService.getDisponibilidade(any())).thenReturn(disponibilidadeEstoqueResponse);
    when(freteCustoService.getFreteCustoPorCEP(anyString())).thenReturn(freteCustoDTO);

    var actual = freteServiceImpl.calcular(freteRequestDTO);

    assertEquals(actual.getPrazoEntrega(), 12);
    assertNotNull(actual.getDataEntrega());
    assertEquals(actual.getValorFrete(), 43.5);
    assertEquals(actual.getMensagem(), SKUS_DISPONIVEIS);
  }
}