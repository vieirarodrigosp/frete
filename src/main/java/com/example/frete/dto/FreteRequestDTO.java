package com.example.frete.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.frete.constants.CommonConstant.VALIDA_CEP;

public class FreteRequestDTO {
  @NotNull
  @Size(min=1, max=5, message = "Lista deve ter entre 1 e 5 ")
  private List<Integer> skuList;
  @NotBlank
  @Pattern(regexp = "\\d{5}-\\d{3}", message = VALIDA_CEP)
  private String cep;

  public FreteRequestDTO() { }

  public List<Integer> getSkuList() {
    return skuList;
  }

  public void setSkuList(List<Integer> skuList) {
    this.skuList = skuList;
  }

  public String getSkuToString() {
    return skuList.stream().map(s -> String.valueOf(s)).collect(Collectors.joining(","));
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }
}