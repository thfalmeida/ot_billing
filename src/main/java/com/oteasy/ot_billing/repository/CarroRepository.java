package com.oteasy.ot_billing.repository;

import java.io.IOException;
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
import com.oteasy.ot_billing.dto.CarroDTO;
import com.oteasy.ot_billing.model.Carro;
import com.oteasy.ot_billing.util.CarroWrapper;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class CarroRepository {

    @Autowired
    Dotenv dotenv;
    private final String DB_URL = "URL.DB_BASE";
    private final String URI_CAR = "URL.CAR";
    private final String URI_LIST = "URL.CAR.LIST";
    private final String URI_NEW = "URL.CAR.NEW";

    public Carro findById(int id) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String car_sufix = dotenv.get(URI_CAR);
        String url = db_url.concat(car_sufix) + id;

         // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try{
            Carro carro = null;
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            carro = objectMapper.readValue(response.body(), Carro.class);
            return carro;
        }catch (JsonParseException e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro durante a comunicação com o Banco de Dados. Contate o administrador");
        }
    }

    public List<CarroDTO> findAll() throws Exception{
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
            CarroWrapper carros = objectMapper.readValue(response.body(), CarroWrapper.class);
            return carros.getItems();
        }catch (JsonParseException e) {
            System.out.println(e.getMessage());
            throw new Exception("Erro durante a comunicação com o Banco de Dados. Contate o administrador");
        }
    }

    public Carro save(Carro carro) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String new_sufix = dotenv.get(URI_NEW);
        String url = db_url.concat(new_sufix);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();

        carro.setId(-1);
        String body = objectMapper.writeValueAsString(carro);

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(body))
                .build();

        Carro carroResponse = null;
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            System.out.println("Statuscode: " + response.statusCode());
            if(response.statusCode() != 201){
                System.out.println(response.body());
                throw new Exception("Carro não criado");
            }
            carroResponse = objectMapper.readValue(response.body(), Carro.class);

        }catch (JsonParseException e) {
            return null;
        }
        return carroResponse;

    }

    public void delete(int id) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String car_sufix = dotenv.get(URI_CAR);
        String url = db_url.concat(car_sufix) + id;

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200)
            throw new Exception("Carro não encontrado");    
    }

    public Carro updateMotorista(int id, Carro carro) throws IOException, InterruptedException{
        String db_url = dotenv.get(DB_URL);
        String driver_sufix = dotenv.get(URI_CAR);
        String url = db_url.concat(driver_sufix) + id;

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(carro);

        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(body))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Carro carroResponse = null;
        carroResponse = objectMapper.readValue(response.body(), Carro.class);

        return carroResponse;
    }

}
