package com.example.frete.controller;

import com.example.frete.dto.FreteRequestDTO;
import com.example.frete.dto.FreteResponseDTO;
import com.example.frete.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping(value = "/fretes")
public class FreteController {
  @Autowired
  private FreteService freteService;
  @PostMapping(value = "/calcular", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public FreteResponseDTO calcular(@Valid @RequestBody FreteRequestDTO freteRequestDTO) {
    return freteService.calcular(freteRequestDTO);
  }
}