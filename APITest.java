import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

public class APITest {
    public static void main(String[] args) {
        String ogCity = "Toronto", deCity = "Montreal", APIkey = APIKeys.getGoogleAPI();
        String urlCall = String.format(
                "https://maps.googleapis.com/maps/api/directions/json?destination=%s&origin=%s&key=%s", ogCity, deCity,
                APIkey);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlCall))
                // .header("X-RapidAPI-Host", "jokes-by-api-ninjas.p.rapidapi.com")
                // .header("X-RapidAPI-Key", "your-rapidapi-key")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response.body());

    }
}
