package com.oteasy.ot_billing.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oteasy.ot_billing.dto.ClienteDTO;
import com.oteasy.ot_billing.model.Cliente;
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
        String db_url = dotenv.get(DB_URL);
        String cliente_prefix = dotenv.get(URI_CLIENT);
        String url = db_url.concat(cliente_prefix) + id;

        HttpClient client = HttpClient.newHttpClient();
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

    public void deleteCliente(int id) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String cliente_prefix = dotenv.get(URI_CLIENT);
        String url = db_url.concat(cliente_prefix) + id;

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200)
            throw new Exception("Erro ao tentar se conectar com o Bando de Dados");    
    }

    public Cliente save(Cliente cliente) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String cliente_prefix = dotenv.get(URI_NEW);
        String url = db_url.concat(cliente_prefix);
        System.out.println(url);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();

        cliente.setId(-1);
        String body = objectMapper.writeValueAsString(cliente);
        System.out.println(body);

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(body))
                .build();

        Cliente clienteResponse = null;
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            System.out.println("Statuscode: " + response.statusCode());
            if(response.statusCode() != 201){
                System.out.println(response.body());
                throw new Exception("Cliente não criado");
            }
            clienteResponse = objectMapper.readValue(response.body(), Cliente.class);
            System.out.println(response.body());

        }catch (JsonParseException e) {
            return null;
        }
        return clienteResponse;
    }

    public Cliente updateCliente(int id, Cliente cliente) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String cliente_prefix = dotenv.get(URI_CLIENT);
        String url = db_url.concat(cliente_prefix) + id;
        System.out.println(url);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(cliente);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();
        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(body))
                .build();

        Cliente clienteResponse = null;
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            System.out.println("Statuscode: " + response.statusCode());
            if(response.statusCode() != 200){
                System.out.println(response.body());
                throw new Exception("Erro ao se conectar com o Bando de Dados");
            }
            System.out.println(response.body());
            clienteResponse = objectMapper.readValue(response.body(), Cliente.class);
            System.out.println(clienteResponse);
        }catch (JsonParseException e) {
            return null;
        }
        return clienteResponse;
    }
}
