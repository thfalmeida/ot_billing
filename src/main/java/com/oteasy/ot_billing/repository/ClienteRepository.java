package com.oteasy.ot_billing.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oteasy.ot_billing.dto.ClienteDTO;
import com.oteasy.ot_billing.model.Cliente;
import com.oteasy.ot_billing.model.Motorista;
import com.oteasy.ot_billing.util.ClienteWrapper;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class ClienteRepository {

    @Autowired
    Dotenv dotenv;

    private final String DB_URL = "URL.DB_BASE";
    private final String URI_CLIENT = "URL.CLIENT";
    private final String URI_LIST = "URL.CLIENT.LIST";
    private final String URI_NEW = "URL.CLIENT.NEW";

    public List<ClienteDTO> findAll() throws IOException, InterruptedException{
        String db_url = dotenv.get(DB_URL);
        String list_sufix = dotenv.get(URI_LIST);
        String url = db_url.concat(list_sufix);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        ClienteWrapper wrapper = null;
        try{
            System.out.println("Statuscode: " + response.statusCode());
            wrapper = objectMapper.readValue(response.body(), ClienteWrapper.class);

        }catch (JsonParseException e) {
            return null;
        }
        return wrapper.getItems();

    }   

    public Cliente findById(int id) throws IOException, InterruptedException{
        

        HttpClient client = HttpClient.newHttpClient();

        String db_url = dotenv.get(DB_URL);
        String cliente_prefix = dotenv.get(URI_CLIENT);
        String url = db_url.concat(cliente_prefix) + id;

        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        Cliente cliente;

        System.out.println(url);
        try{
            cliente = objectMapper.readValue(response.body(), Cliente.class);
        }catch (JsonParseException e) {
            return null;
        }

        return cliente;
    }
}
