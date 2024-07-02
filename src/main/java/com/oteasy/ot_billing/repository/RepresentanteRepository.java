package com.oteasy.ot_billing.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.net.http.HttpRequest.BodyPublishers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oteasy.ot_billing.dto.RepresentanteDTO;
import com.oteasy.ot_billing.model.Representante;
import com.oteasy.ot_billing.util.RepresentanteWrapper;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class RepresentanteRepository {

    @Autowired
    Dotenv dotenv;
    private final String DB_URL = "URL.DB_BASE";
    private final String URI_AGENT = "URL.AGENT";
    private final String URI_LIST = "URL.AGENT.LIST";
    private final String URI_NEW = "URL.AGENT.NEW";

    public Representante findById(int id) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String url_sufix = dotenv.get(URI_AGENT);
        String url = db_url.concat(url_sufix) + id;

         // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try{
            Representante representante = null;
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            representante = objectMapper.readValue(response.body(), Representante.class);
            return representante;
        }catch (JsonParseException e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro durante a comunicação com o Banco de Dados. Contate o administrador");
        }
    }

    public List<RepresentanteDTO> findAll() throws Exception{
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
            RepresentanteWrapper representantes = objectMapper.readValue(response.body(), RepresentanteWrapper.class);
            return representantes.getItems();
        }catch (JsonParseException e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro durante a comunicação com o Banco de Dados. Contate o administrador");
        }
    }

    public Representante save(Representante representante) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String new_sufix = dotenv.get(URI_NEW);
        String url = db_url.concat(new_sufix);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();

        representante.setId(-1);
        String body = objectMapper.writeValueAsString(representante);

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
            Representante representanteResponse = null;
            representanteResponse = objectMapper.readValue(response.body(), Representante.class);
            return representanteResponse;

        }catch (JsonParseException e) {
            return null;
        }
    }

    public void delete(int id) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String url_sufix = dotenv.get(URI_AGENT);
        String url = db_url.concat(url_sufix) + id;

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200)
            throw new Exception("Representante não encontrado");    
    }

    public Representante updateMotorista(int id, Representante representante) throws IOException, InterruptedException{
        String db_url = dotenv.get(DB_URL);
        String url_sufix = dotenv.get(URI_AGENT);
        String url = db_url.concat(url_sufix) + id;

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(representante);

        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(body))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Representante representanteResponse = objectMapper.readValue(response.body(), Representante.class);

        return representanteResponse;
    }
}
