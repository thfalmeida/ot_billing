package com.oteasy.ot_billing.repository;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oteasy.ot_billing.model.Carro;
import com.oteasy.ot_billing.model.Cliente;
import com.oteasy.ot_billing.model.Motorista;
import com.oteasy.ot_billing.model.ObjectModel;
import com.oteasy.ot_billing.model.Representante;
import com.oteasy.ot_billing.model.Transporte;
import com.oteasy.ot_billing.model.Viagem;
import com.oteasy.ot_billing.util.HttpMethod;
import com.oteasy.ot_billing.util.HttpRequester;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class Repository {

    @Autowired
    Dotenv dotenv;
    @Autowired
    HttpRequester httpRequester;

    private final String URL_LIST = "URL.LIST";
    private final String URL_NEW = "URL.NEW";
    private final String URL_DRIVER = "URL.DRIVER";
    private final String URL_CAR = "URL.CAR";
    private final String URL_CLIENT = "URL.CLIENT";
    private final String URL_TRIP = "URL.TRIP";
    private final String URL_AGENT = "URL.AGENT";
    
   
    public <T> List<ObjectModel> FindAll(Class<T> classType) throws IOException, InterruptedException{
        ObjectMapper objectMapper = new ObjectMapper();

        String uri = buildURI(classType).concat(dotenv.get(URL_LIST));
        System.out.println(uri);
        HttpResponse<String> response = httpRequester.SendRequest(uri, HttpMethod.GET);

        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode itemsNode = rootNode.path("items");
        List<ObjectModel> lista = objectMapper.readerFor(List.class).readValue(itemsNode);

        return lista;
    }


    public <T> ObjectModel findById(int id, Class<T> classType) throws IOException, InterruptedException{
        String uri = buildURI(classType) + id;

        HttpResponse<String> response = httpRequester.SendRequest(uri, HttpMethod.GET);
        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();

        return (ObjectModel) objectMapper.readValue(response.body(), classType);
    }

    public <T> ObjectModel save(int id, ObjectModel obj,Class<T> classType) throws IOException, InterruptedException{
        System.out.println(obj.getClass() == classType);
        String url = buildURI(classType) + id;

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(obj); 
        HttpResponse<String> response = httpRequester.SendRequest(url, HttpMethod.POST, body);
        return (ObjectModel) objectMapper.readValue(response.body(), classType);
    }

    public <T> void delete(int id, Class<T> classType) throws Exception{
        String url = buildURI(classType) + id;

        httpRequester.SendRequest(url, HttpMethod.DELETE)  ;
    }

    public <T> ObjectModel save(ObjectModel obj, Class<T> classType) throws IOException, InterruptedException{
        System.out.println(obj.getClass() == classType);
        String url = buildURI(classType).concat(dotenv.get(URL_NEW));

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(obj); 
        HttpResponse<String> response = httpRequester.SendRequest(url, HttpMethod.POST, body);
        return (ObjectModel) objectMapper.readValue(response.body(), classType);
    }


    private <T> String buildURI(Class<T> classType ){
        String db_url = dotenv.get("URL.DB_BASE");
        String path;

        if(classType.isAssignableFrom(Cliente.class)){
            path = dotenv.get(URL_CLIENT);
            return db_url.concat(path);
        }
        else if(classType.isAssignableFrom(Carro.class)){
            path = dotenv.get(URL_CAR);
            return db_url.concat(path);
        }
        else if(classType.isAssignableFrom(Viagem.class)){
            path = dotenv.get(URL_TRIP);
            return db_url.concat(path);
        }
        else if(classType.isAssignableFrom(Representante.class)){
            path = dotenv.get(URL_AGENT);
            return db_url.concat(path);
        }    
        else if(classType.isAssignableFrom(Motorista.class)){
            path = dotenv.get(URL_DRIVER);
            return db_url.concat(path);
        }  
        else if(classType.isAssignableFrom(Transporte.class)){
            path = dotenv.get(URL_TRIP);
            return db_url.concat(path);
        }  
        return "";                         
    }
}
