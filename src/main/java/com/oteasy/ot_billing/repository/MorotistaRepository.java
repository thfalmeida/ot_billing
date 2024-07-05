package com.oteasy.ot_billing.repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.oteasy.ot_billing.model.Motorista;
import com.oteasy.ot_billing.util.MotoristaWrapper;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class MorotistaRepository {
    
    @Autowired
    Dotenv dotenv;
    private final String DB_URL = "URL.DB_BASE";
    private final String URI_DRIVER = "URL.DRIVER";
    private final String URI_LIST = "URL.DRIVER.LIST";
    private final String URI_NEW = "URL.DRIVER.NEW";

    public Optional<Motorista> findById(int id) throws IOException, InterruptedException{
        String db_url = dotenv.get(DB_URL);
        String driver_sufix = dotenv.get(URI_DRIVER);
        String url = db_url.concat(driver_sufix);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();
        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            Motorista motorista = objectMapper.readValue(response.body(), Motorista.class);
            Optional<Motorista> optMotorista = Optional.ofNullable(motorista);
            return optMotorista;
        }catch (JsonParseException e) {
            Optional<Motorista> optMotorista = Optional.ofNullable(null);
            return optMotorista;
        }
    }

    public List<Motorista> listAll() throws Exception{
        //Constroi a URL
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

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            MotoristaWrapper wrapper = objectMapper.readValue(response.body(), MotoristaWrapper.class);
            return wrapper.getItems();
        }catch (JsonParseException e) {
            throw new Exception("Erro durante a comunicação com o Banco de Dados. Contate o administrador");
        }
    }

    public Motorista save(Motorista motorista) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String new_sufix = dotenv.get(URI_NEW);
        String url = db_url.concat(new_sufix);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();

        motorista.setId(-1);
        String body = objectMapper.writeValueAsString(motorista);

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(body))
                .build();

        Motorista motoristaResponse = null;
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            System.out.println("Statuscode: " + response.statusCode());
            if(response.statusCode() != 201){
                System.out.println(response.body());
                throw new Exception("Motorista não criado");
            }
            motoristaResponse = objectMapper.readValue(response.body(), Motorista.class);

        }catch (JsonParseException e) {
            return null;
        }
        return motoristaResponse;

    }

    public void deleteMotorista(int id) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String driver_sufix = dotenv.get(URI_DRIVER);
        String url = db_url.concat(driver_sufix) + id;

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200)
            throw new Exception("Motorista não encontrado");    
    }

    public Motorista updateMotorista(int id, Motorista motorista) throws IOException, InterruptedException{
        String db_url = dotenv.get(DB_URL);
        String driver_sufix = dotenv.get(URI_DRIVER);
        String url = db_url.concat(driver_sufix) + id;

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(motorista);

        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(body))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Motorista motoristaResponse = null;
        System.out.println(response.body());

        motoristaResponse = objectMapper.readValue(response.body(), Motorista.class);
        return motoristaResponse;
        
    }
}
