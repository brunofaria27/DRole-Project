package lib;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Recommender {
    private static final String MODEL_URL = "http://337f59df-5b23-41b3-8d3c-044cb54a2dd9.brazilsouth.azurecontainer.io/score";


    public String classify(String hard) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(MODEL_URL))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(hard))
                .build();

        String classifiedFeatures = null;
        try {
            HttpResponse<String> response  = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return classifiedFeatures;
    }


}