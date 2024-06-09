import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GoogleMaps {

   /**
    * Stores the Google Maps API key and the MapBox API key in APIKeys.java.
    * Separate file for API keys because I want to push the other files to GitHub
    * without
    * exposing the API keys.
    */

   /**
    * Returns the name of the location given the latitude and longitude
    * 
    * @param lati  - double - latitude of the location
    * @param longi - double - longitude of the location
    * @return String - name of the location after calling the Google Maps API
    */

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

   /**
    * Returns the polyline string given the latitude and longitude of the start and
    * end points
    * 
    * @param lati1  - double - latitude of the start point
    * @param longi1 - double - longitude of the start point
    * @param lati2  - double - latitude of the end point
    * @param longi2 - double - longitude of the end point
    * @return String - encoded polyline string after calling the Google Maps API
    */
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
            "            \"points\" : \"")
            + ("overview_polyline\" : \n" + //
                  "         {\n" + //
                  "            \"points\" : \"").length(),
            response.body().indexOf("\"\n" + //
                  "         },\n" + //
                  "         \"summary\""));
   }

   public static String getPolyline(String address1, String address2) {
      try {
         address1 = URLEncoder.encode(address1, "UTF-8");
         address2 = URLEncoder.encode(address2, "UTF-8");
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }
      String urlCall = String.format(
            "https://maps.googleapis.com/maps/api/directions/json?destination=%s&origin=%s&key=%s", address1, address2,
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
      return response.body().substring(response.body().indexOf("overview_polyline\" : \n" + //
            "         {\n" + //
            "            \"points\" : \"")
            + ("overview_polyline\" : \n" + //
                  "         {\n" + //
                  "            \"points\" : \"").length(),
            response.body().indexOf("\"\n" + //
                  "         },\n" + //
                  "         \"summary\""));
   }

   public static void main(String[] args) {
      // System.out.println(getName(33.4409, -111.8608));
      // System.out.println(getPolyline(33.57886151894017, -111.98955893149417,
      // 33.473531150492086, -111.99264822733647));
      // System.out.println(Arrays.toString(getLatLng("1600 Amphitheatre Parkway,
      // Mountain View, CA")));
      System.out.println(getPolyline("1600 Amphitheatre Parkway, Mountain View, CA", "820 E Apache Blvd, Tempe, AZ"));
   }
}
