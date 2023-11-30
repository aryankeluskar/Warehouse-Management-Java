import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GoogleMaps {
   public static String getName(double lati, double longi) {
      String urlCall = String.format(
            "https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s", lati, longi,
            APIKeys.getGoogleAPI());
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
      return (response.body().substring(
            response.body().indexOf("formatted_address\" : ") + "formatted_address\" : ".length() + 1,
            response.body().indexOf("\",\n" + //
                  "         \"geometry\" : ")));
   }

   public static String getPolyline(double lati1, double longi1, double lati2, double longi2) {
      String urlCall = String.format(
            "https://maps.googleapis.com/maps/api/directions/json?destination=%f,%f&origin=%f,%f&key=%s", lati1,
            longi1, lati2, longi2, APIKeys.getGoogleAPI());
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
      return response.body().substring(response.body().indexOf("overview_polyline\" : \n" + //
            "         {\n" + //
            "            \"points\" : \"")+("overview_polyline\" : \n" + //
                  "         {\n" + //
                  "            \"points\" : \"").length(), response.body().indexOf("\"\n" + //
                        "         },\n" + //
                        "         \"summary\""));
   }

   public static void main(String[] args) {
      // System.out.println(getName(33.4409, -111.8608));
      System.out.println(getPolyline(33.57886151894017, -111.98955893149417, 33.473531150492086, -111.99264822733647));
   }
}
