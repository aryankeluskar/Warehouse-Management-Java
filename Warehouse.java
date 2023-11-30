import java.util.ArrayList;

public class Warehouse {
   private static String name;
   private static double id;
   private static double[] location;
   public static ArrayList<Truck> fleet;

   public Warehouse(String name, double id, double longitude, double latitude) {
      Warehouse.name = name;
      Warehouse.id = id;
      Warehouse.location = new double[]{longitude, latitude};
      Warehouse.fleet = new ArrayList<Truck>();
   }

   // Getters and setters for the attributes if needed

   public static ArrayList<Truck> getTrucks() {
      return fleet;
   }

   public static String getName() {
      return name;
   }

   public static void setName(String name) {
      Warehouse.name = name;
   }

   public static double getId() {
      return id;
   }

   public static void setId(double id) {
      Warehouse.id = id;
   }

   public static String getLocation() {
      return String.format("({%f},{%f})", location[0], location[1]);
   }

   public static void setLocation(double longitude, double latitude) {
      Warehouse.location[0] = longitude;
      Warehouse.location[1] = latitude;
   }

   public static ArrayList<Truck> getFleet() {
      return fleet;
   }

   public static void setFleet(ArrayList<Truck> fleet) {
      Warehouse.fleet = fleet;
   }

   public static void addTruck(Truck truck) {
      fleet.add(truck);
   }

   public static void updateTruck(int index, Truck updatedTruck) {
      if (index >= 0 && index < fleet.size()) {
         fleet.set(index, updatedTruck);
      } else {
         System.out.println("Invalid index");
      }
   }

   public static void removeTruck(int index) {
      if (index >= 0 && index < fleet.size()) {
         fleet.remove(index);
      } else {
         System.out.println("Invalid index");
      }
   }
}
