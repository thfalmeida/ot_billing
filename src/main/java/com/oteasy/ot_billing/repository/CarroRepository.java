package com.oteasy.ot_billing.repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oteasy.ot_billing.model.Carro;

@Component
public class CarroRepository {

    public Carro findById(int id) throws IOException, InterruptedException{
         // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://apex.oracle.com/pls/apex/apptcc/car/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        Carro carro = null;
        try{
            carro = objectMapper.readValue(response.body(), Carro.class);
        }catch (JsonParseException e) {
            return null;
        }

        return carro;
    }
}
