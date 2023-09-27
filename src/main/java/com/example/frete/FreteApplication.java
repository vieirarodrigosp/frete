package com.example.frete;

import com.example.frete.httpClient.EstoqueClient;
import com.example.frete.httpClient.FreteCustoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {EstoqueClient.class, FreteCustoClient.class})
@SpringBootApplication
public class FreteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreteApplication.class, args);
	}

}
