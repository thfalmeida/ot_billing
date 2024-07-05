package com.oteasy.ot_billing.repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.net.http.HttpRequest.BodyPublishers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oteasy.ot_billing.dto.CarroDTO;
import com.oteasy.ot_billing.model.Carro;
import com.oteasy.ot_billing.util.CarroWrapper;
import com.oteasy.ot_billing.util.ResourceNotFoundException;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class CarroRepository {

    @Autowired
    Dotenv dotenv;
    private final String DB_URL = "URL.DB_BASE";
    private final String URI_CAR = "URL.CAR";
    private final String URI_LIST = "URL.CAR.LIST";
    private final String URI_NEW = "URL.CAR.NEW";

    public Optional<Carro> findById(int id){
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
            if(response.statusCode() != 200)
                throw new ResourceNotFoundException("Carro não encontrado");

            ObjectMapper objectMapper = new ObjectMapper();
            carro = objectMapper.readValue(response.body(), Carro.class);
            Optional<Carro> optCarro = Optional.ofNullable(carro);
            return optCarro;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.ofNullable(null);
        }
    }

    public List<CarroDTO> findAll(){
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
            CarroWrapper carros = objectMapper.readValue(response.body(), CarroWrapper.class);
            return carros.getItems();
        }catch (Exception e) {
            return null;
        }
    }

    public Carro save(Carro carro){
        String db_url = dotenv.get(DB_URL);
        String new_sufix = dotenv.get(URI_NEW);
        String url = db_url.concat(new_sufix);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();

        try{
            carro.setId(-1);
            String body = objectMapper.writeValueAsString(carro);

            // Cria uma solicitação GET
            HttpRequest request = HttpRequest.newBuilder() 
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(body))
                    .build();

            Carro carroResponse = null;
        
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            System.out.println("Statuscode: " + response.statusCode());
            if(response.statusCode() != 201){
                System.out.println(response.body());
                throw new Exception("Erro ao conectar com o banco de dados");
            }
            carroResponse = objectMapper.readValue(response.body(), Carro.class);
            return carroResponse;

        }catch (Exception e) {

            return null;
        }
    }

    public void delete(int id){
        String db_url = dotenv.get(DB_URL);
        String car_sufix = dotenv.get(URI_CAR);
        String url = db_url.concat(car_sufix) + id;

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .DELETE()
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200 || response.statusCode() != 201)
                throw new Exception("Erro ao se conectar com o banco de dados");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
         
    }

    public Carro updateCarro(int id, Carro carro){
        String db_url = dotenv.get(DB_URL);
        String driver_sufix = dotenv.get(URI_CAR);
        String url = db_url.concat(driver_sufix) + id;

        ObjectMapper objectMapper = new ObjectMapper();
        try{
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
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
