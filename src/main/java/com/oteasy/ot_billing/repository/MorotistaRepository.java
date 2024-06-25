package com.oteasy.ot_billing.repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.oteasy.ot_billing.model.Motorista;
import com.oteasy.ot_billing.dto.*;
import com.oteasy.ot_billing.util.MotoristaWrapper;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class MorotistaRepository {
    
    @Autowired
    Dotenv dotenv;
    private final String DB_URL = "URL.DB_BASE";
    private final String URI_DRIVER = "URL.DRIVER.BASE";
    private final String URI_LIST = "URL.DRIVER.LIST";
    private final String URI_NEW = "URL.DRIVER.NEW";

    public Motorista findById(int id) throws IOException, InterruptedException{
        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        String db_url = dotenv.get(DB_URL);
        String driver_sufix = dotenv.get(URI_DRIVER);
        String url = db_url.concat(driver_sufix);

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + id))
                .GET()
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        Motorista motorista = null;
        try{
            motorista = objectMapper.readValue(response.body(), Motorista.class);
        }catch (JsonParseException e) {
            return null;
        }

        return motorista;
    }

    public List<MotoristaDTO> listAll() throws IOException, InterruptedException{
        //Constroi a URL
        String db_url = dotenv.get(DB_URL);
        String driver_sufix = dotenv.get(URI_DRIVER);
        String list_sufix = dotenv.get(URI_LIST);
        String url = db_url.concat(driver_sufix).concat(list_sufix);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        MotoristaWrapper wrapper = null;
        try{
            System.out.println("Statuscode: " + response.statusCode());
            wrapper = objectMapper.readValue(response.body(), MotoristaWrapper.class);

        }catch (JsonParseException e) {
            return null;
        }
        return wrapper.getItems();
    }

    public Motorista save(Motorista motorista) throws Exception{
        String db_url = dotenv.get(DB_URL);
        String driver_sufix = dotenv.get(URI_DRIVER);
        String new_sufix = dotenv.get(URI_NEW);
        String url = db_url.concat(driver_sufix).concat(new_sufix);

        // Cria uma instância de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(motorista.getNome() + " " + motorista.getTelefone());
        motorista.setId(-1);
        String body = objectMapper.writeValueAsString(motorista);
        System.out.println(body);

        // Cria uma solicitação GET
        HttpRequest request = HttpRequest.newBuilder() 
                .uri(URI.create(url))
                .POST(BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        Motorista motoristaResponse = null;
        try{
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
}
