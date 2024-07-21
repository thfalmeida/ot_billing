package com.oteasy.ot_billing.util;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;


import org.springframework.stereotype.Component;

@Component
public class HttpRequester{

    public HttpResponse<String> SendRequest(String url, HttpMethod method, String body) throws IOException, InterruptedException{
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.uri(URI.create(url));
        switch (method) {
            case HttpMethod.GET:
                requestBuilder.GET();
                break;
            case HttpMethod.POST:
                requestBuilder.POST(BodyPublishers.ofString(body));
                break;
            case HttpMethod.PUT:
                requestBuilder.PUT(BodyPublishers.ofString(body));
                break;
            case HttpMethod.DELETE:
                requestBuilder.DELETE();
                break;
            default:
                break;
        }

        HttpRequest request = requestBuilder.build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> SendRequest(String url, HttpMethod method) throws IOException, InterruptedException{
        return SendRequest(url, method, "");
    }
    
}
