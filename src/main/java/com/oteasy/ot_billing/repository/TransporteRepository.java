package com.oteasy.ot_billing.repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.net.http.HttpRequest.BodyPublishers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oteasy.ot_billing.dto.TransporteDTO;
import com.oteasy.ot_billing.model.Transporte;
import com.oteasy.ot_billing.util.TransporteWrapper;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class TransporteRepository {

    @Autowired
    Dotenv dotenv;
    private final String DB_URL = "URL.DB_BASE";
    private final String URI_SUFIX = "URL.TRANSPORT";
    private final String URI_LIST = "URL.TRANSPORT.LIST";
    private final String URI_NEW = "URL.TRANSPORT.NEW";

    public Transporte findById(int id) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String url_sufix = dotenv.get(URI_SUFIX);
        String url = db_url.concat(url_sufix) + id;

         // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try{
            Transporte transporte = null;
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            transporte = objectMapper.readValue(response.body(), Transporte.class);
            return transporte;
        }catch (JsonParseException e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro durante a comunicação com o Banco de Dados. Contate o administrador");
        }
    }

    public List<TransporteDTO> findAll() throws Exception{
        String db_url = dotenv.get(DB_URL);
        String list_sufix = dotenv.get(URI_LIST);
        String url = db_url.concat(list_sufix);

        System.out.println(url);
         // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            TransporteWrapper transportes = objectMapper.readValue(response.body(), TransporteWrapper.class);
            return transportes.getItems();
        }catch (JsonParseException e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro durante a comunicação com o Banco de Dados. Contate o administrador");
        }
    }

    public Transporte save(Transporte transporte) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String new_sufix = dotenv.get(URI_NEW);
        String url = db_url.concat(new_sufix);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();

        transporte.setId(-1);
        String body = objectMapper.writeValueAsString(transporte);

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(body))
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            System.out.println("Statuscode: " + response.statusCode());
            if(response.statusCode() != 201){
                System.out.println(response.body());
                throw new Exception("Carro não criado");
            }
            Transporte transporteResponse = null;
            transporteResponse = objectMapper.readValue(response.body(), Transporte.class);
            return transporteResponse;
        }catch (JsonParseException e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro durante a comunicação com o Banco de Dados. Contate o administrador");
        }

    }

    public void delete(int id) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String url_sufix = dotenv.get(URI_SUFIX);
        String url = db_url.concat(url_sufix) + id;

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200)
            throw new Exception("Transporte não encontrado");    
    }

    public Transporte update(int id, Transporte transporte) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String uri_sufix = dotenv.get(URI_SUFIX);
        String url = db_url.concat(uri_sufix) + id;

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(transporte);

        HttpClient client = HttpClient.newHttpClient();

        try{
        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(body))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Transporte transporteResponse = null;
        transporteResponse = objectMapper.readValue(response.body(), Transporte.class);

        return transporteResponse;
        } catch (JsonParseException e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro durante a comunicação com o Banco de Dados. Contate o administrador");
        }
    }

}
