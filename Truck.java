public class Truck {
    private String name;
    private String numberPlate;
    private String[] goods;
    private double singleDriveTime;
    private double[] currentLocation;
    private double[] routeBegin;
    private double[] routeEnd;
    private double totalDriveTime;
    private double drivingSpeed;
    private double fuelEfficiency;
    private boolean tampering;
    private boolean collisions;

    // Constructor
    public Truck(String name, String numberPlate, String[] goods, double singleDriveTime, double longitude,
            double latitude,
            double drivingSpeed, double fuelEfficiency, boolean tampering, boolean collisions) {
        this.name = name;
        this.numberPlate = numberPlate;
        this.goods = goods;
        this.singleDriveTime = singleDriveTime;
        this.currentLocation = new double[] { longitude, latitude };
        this.drivingSpeed = drivingSpeed;
        this.fuelEfficiency = fuelEfficiency;
        this.tampering = tampering;
        this.collisions = collisions;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public double[] getcurrentLocation() {
        return currentLocation;
    }

    public void setcurrentLocation(double[] currentLocation) {
        this.currentLocation = currentLocation;
    }

    // Getter and Setter for drivingSpeed
    public double getDrivingSpeed() {
        return drivingSpeed;
    }

    public void setDrivingSpeed(double drivingSpeed) {
        this.drivingSpeed = drivingSpeed;
    }

    // Getter and Setter for fuelEfficiency
    public double getFuelEfficiency() {
        return fuelEfficiency;
    }

    public void setFuelEfficiency(double fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    // Getter and Setter for tampering
    public boolean isTampering() {
        return tampering;
    }

    public void setTampering(boolean tampering) {
        this.tampering = tampering;
    }

    // Getter and Setter for collisions
    public boolean isCollisions() {
        return collisions;
    }

    public void setCollisions(boolean collisions) {
        this.collisions = collisions;
    }

    // Method to calculate distance traveled
    public double calculateDistanceTraveled() {
        return drivingSpeed * singleDriveTime;
    }

    // Method to check if the truck needs refueling
    public boolean needsRefueling() {
        double distance = calculateDistanceTraveled();
        double fuelConsumed = distance / fuelEfficiency;
        return fuelConsumed >= 0.99;
    }

    // Method to simulate refueling the truck
    public void refuel() {
        totalDriveTime += singleDriveTime;
        singleDriveTime = 0.0;
    }
}
