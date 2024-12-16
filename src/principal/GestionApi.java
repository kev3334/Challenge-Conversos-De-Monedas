package principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GestionApi {

    public String getJson(String monedaBase) throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/fd2791b3198fc706d3c284e4/latest/"+monedaBase;
        String json;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        json = response.body();

        return json;

    }
}
