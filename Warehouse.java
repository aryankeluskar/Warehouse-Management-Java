import java.util.ArrayList;

public class Warehouse {
   private String name;
   private double id;
   private double[] location;
   public ArrayList<Truck> fleet;

   public Warehouse(String name, double id, double longitude, double latitude) {
      this.name = name;
      this.id = id;
      this.location = new double[] { longitude, latitude };
      this.fleet = new ArrayList<Truck>();
   }

   // Getters and setters for the attributes if needed

   public ArrayList<Truck> getTrucks() {
      return fleet;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public double getId() {
      return id;
   }

   public void setId(double id) {
      this.id = id;
   }

   public String getLocation() {
      return String.format("({%f},{%f})", location[0], location[1]);
   }

   public double getLongitude() {
      return location[0];
   }

   public double getLatitude() {
      return location[1];
   }

   public void setLocation(double longitude, double latitude) {
      this.location[0] = longitude;
      this.location[1] = latitude;
   }

   public ArrayList<Truck> getFleet() {
      return fleet;
   }

   public void setFleet(ArrayList<Truck> fleet) {
      this.fleet = fleet;
   }

   public void addTruck(Truck truck) {
      fleet.add(truck);
   }

   public void updateTruck(int index, Truck updatedTruck) {
      if (index >= 0 && index < fleet.size()) {
         fleet.set(index, updatedTruck);
      } else {
         System.out.println("Invalid index");
      }
   }

   public void removeTruck(int index) {
      if (index >= 0 && index < fleet.size()) {
         fleet.remove(index);
      } else {
         System.out.println("Invalid index");
      }
   }
}
