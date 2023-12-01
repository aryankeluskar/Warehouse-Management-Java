public class Truck {
   public String name;
   private String numberPlate;
   private String[] goods;
   private double singleDriveTime;
   private double[] currentLocation;
   public double[] routeBegin;
   public double[] routeEnd;
   private String polylineString;
   private double totalDriveTime;
   private double drivingSpeed;
   private double fuelEfficiency;

   // Constructor
   public Truck(String name, String numberPlate, String[] goods, double singleDriveTime, double longitude,
         double latitude, double[] routeBegin, double[] routeEnd,
         double drivingSpeed, double fuelEfficiency, String polylineString) {
      this.name = name;
      this.numberPlate = numberPlate;
      this.goods = goods;
      this.singleDriveTime = singleDriveTime;
      this.currentLocation = new double[] { longitude, latitude };
      this.routeBegin = routeBegin;
      this.routeEnd = routeEnd;
      this.drivingSpeed = drivingSpeed;
      this.fuelEfficiency = fuelEfficiency;
      this.polylineString = polylineString;
   }

   // Getter and Setter for name
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   // Getter and Setter for polylineString
   public String getPolylineString() {
      return polylineString;
   }

   // Getter and Setter for numberPlate
   public String getNumberPlate() {
      return numberPlate;
   }

   public void setNumberPlate(String numberPlate) {
      this.numberPlate = numberPlate;
   }

   // Getter and Setter for goods
   public String[] getGoods() {
      return goods;
   }

   public void setGoods(String[] goods) {
      this.goods = goods;
   }

   // Getter and Setter for singleDriveTime
   public double getsingleDriveTime() {
      return singleDriveTime;
   }

   public void setsingleDriveTime(double singleDriveTime) {
      this.singleDriveTime = singleDriveTime;
   }

   // Getter and Setter for currentLocation
   public double[] getCurrentLocation() {
      return currentLocation;
   }

   public void setCurrentLocation(double[] currentLocation) {
      this.currentLocation = currentLocation;
   }

   // Getter and Setter for routeBegin
   public double[] getRouteBegin() {
      return routeBegin;
   }

   public void setRouteBegin(double[] routeBegin) {
      this.routeBegin = routeBegin;
   }

   // Getter and Setter for routeEnd
   public double[] getRouteEnd() {
      return routeEnd;
   }

   public void setRouteEnd(double[] routeEnd) {
      this.routeEnd = routeEnd;
   }

   // Getter and Setter for totalDriveTime
   public double getTotalDriveTime() {
      return totalDriveTime;
   }

   // Getter and Setter for drivingSpeed
   public double getDrivingSpeed() {
      return drivingSpeed;
   }

   public double getFuelEfficiency() {
      return fuelEfficiency;
   }

}
