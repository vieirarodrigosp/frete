package com.example.frete.controller;

import com.example.frete.dto.FreteResponseDTO;
import com.example.frete.service.FreteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

import static com.example.frete.constants.CommonConstant.VALIDA_CEP;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FreteController.class)
class FreteControllerTest {
  @Autowired
  private MockMvc mvc;
  @MockBean
  private FreteService freteService;
  @Test
  void calcular_retornoDeveSer202isAccepted() throws Exception {
    var payload = "{\"skuList\":[10000,54000,546446,65000,25000],\"cep\":\"04654-654\"}";

    when(freteService.calcular(any())).thenReturn(new FreteResponseDTO(12, LocalDateTime.now(), 43.5, "Lista de skus indisponíveis: 50000, 54000, 546446, 25000"));

    mvc.perform(MockMvcRequestBuilders
            .post("/fretes/calcular")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(payload))
        .andDo(print())
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.prazoEntrega").value("12"))
        .andExpect(jsonPath("$.dataEntrega").isNotEmpty())
        .andExpect(jsonPath("$.valorFrete").value(43.5))
        .andExpect(jsonPath("$.mensagem").value("Lista de skus indisponíveis: 50000, 54000, 546446, 25000"));
  }
  @Test
  void calcular_isBadRequest_pelaListaDeSkusSerMaiorQueCinco() throws Exception {
    var payload = "{\"skuList\":[10000,54000,546446,65000,25000, 25324450],\"cep\":\"04654-654\"}";

    mvc.perform(MockMvcRequestBuilders
            .post("/fretes/calcular")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(payload))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
        .andExpect(result -> result.getResolvedException().getMessage().contains("Lista deve ter entre 1 e 5"));
  }
  @Test
  void calcular_isBadRequest_skuListNaoPodeSerNulo() throws Exception {
    var payload = "{\"cep\":\"04654-654\"}";

    mvc.perform(MockMvcRequestBuilders
            .post("/fretes/calcular")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(payload))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
        .andExpect(result -> result.getResolvedException().getMessage().contains("message [skuList]]; default message [must not be null]]"));
  }
  @Test
  void calcular_isBadRequest_peloCEPEstarnoFormatoInvalido() throws Exception {
    var payload = "{\"skuList\":[10000,54000,546446,65000,25000],\"cep\":\"4654-654\"}";

    mvc.perform(MockMvcRequestBuilders
            .post("/fretes/calcular")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(payload))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
        .andExpect(result -> result.getResolvedException().getMessage().contains(VALIDA_CEP));
  }
  @Test
  void calcular_isBadRequest_peloCEP_NaoPodeSerNulo() throws Exception {
    var payload = "{\"skuList\":[10000,54000,546446,65000,25000]}";

    mvc.perform(MockMvcRequestBuilders
            .post("/fretes/calcular")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(payload))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
        .andExpect(result -> result.getResolvedException().getMessage().contains("default message [cep]]; default message [must not be blank]"));
  }
}