package com.example.frete.constants;

public class CommonConstant {
  private CommonConstant() {
    throw new UnsupportedOperationException(NAO_PODE_SER_INSTANCIADA);
  }
  public static final String NAO_PODE_SER_INSTANCIADA = "Esta é uma classe utilitária e não pode ser instanciada";
  public static final String CONTRATO_DISPONIBILIDADE_ESTOQUE = "{\"disponibilidade-estoque\":[{\"sku\":10000,\"disponibilidade\":true,\"tempo-expedicao\":2},{\"sku\":50000,\"disponibilidade\":false,\"tempo-expedicao\":null},{\"sku\":65000,\"disponibilidade\":true,\"tempo-expedicao\":10}],\"tempo-resposta\":0.01,\"mensagem\":null}";
  public static final String DISPONIBILIDADE_ESTOQUE_NAO_ENCONTRADA = "Desculpe-nos, mas no momento não temos estoque do(s) SKU(s) ";
  public static final String CEP_NAO_ENCONTRADA = "Desculpe-nos, mas no momento não podemos entregar no seu endereço, cep: ";
  public static final String CONTRATO_MOCK_CEP = "{\"transportadora\":\"ASAPLOG\",\"valor-frete\":43.50,\"prazo-entrega\":2,\"cep\":\"20771-000\",\"http_status\":200,\"mensagem\":null}";
  public static final String VALIDA_CEP = "deve corresponder uma sequência de 5 caracteres numéricos, depois um traço (- traço) e mais 3 caracteres numéricos";
  public static final String SKUS_DISPONIVEIS = "Todos os skus estão disponíveis para entrega";
  public static final String SKUS_INDISPONIVEIS = "Lista de skus indisponíveis: ";
}