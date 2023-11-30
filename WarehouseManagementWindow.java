import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Random;

public class WarehouseManagementWindow extends JFrame {
   private JLabel warehouseLabel;
   private JLabel warehouseNameLabel;
   private JComboBox<String> truckDropdown;
   private JTable truckInfoTable;
   private JButton addTruckButton;
   private JButton deleteTruckButton;
   private JButton updateTruckButton;
   private JButton openRouteURLButton;
   private Warehouse warehouse; // Add Warehouse object

   public WarehouseManagementWindow() {
      // Set window properties
      setTitle("Warehouse Management");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new BorderLayout());
      setSize(1200, 600);

      // Create right pane with website
      JPanel rightPane = new JPanel();
      rightPane.setBackground(Color.WHITE);
      rightPane.add(new JLabel("Website"));
      

      // Create left pane with first section having 20% height, second section having
      // 50% height and third section having 30% height
      JPanel leftPane = new JPanel();
      leftPane.setLayout(new GridLayout(3, 1));

      // Top left section for warehouse labels
      JPanel topLeftPanel = new JPanel();
      topLeftPanel.setLayout(new FlowLayout());
      warehouseLabel = new JLabel("Warehouse");
      warehouseNameLabel = new JLabel("Warehouse Name");
      topLeftPanel.add(warehouseLabel);
      topLeftPanel.add(warehouseNameLabel);

      // Middle left section for truck dropdown and info table
      JPanel middleLeftPanel = new JPanel();
      middleLeftPanel.setLayout(new BorderLayout());
      truckDropdown = new JComboBox<>();
      truckInfoTable = new JTable();
      JScrollPane truckInfoScrollPane = new JScrollPane(truckInfoTable);
      middleLeftPanel.add(truckDropdown, BorderLayout.NORTH);
      middleLeftPanel.add(truckInfoScrollPane, BorderLayout.CENTER);

      // Bottom left section for buttons
      JPanel bottomLeftPanel = new JPanel();
      bottomLeftPanel.setLayout(new GridLayout(2, 2));
      addTruckButton = new JButton("Add Truck");
      deleteTruckButton = new JButton("Delete Truck");
      updateTruckButton = new JButton("Update Truck");
      openRouteURLButton = new JButton("Open Route URL");
      bottomLeftPanel.add(addTruckButton);
      bottomLeftPanel.add(deleteTruckButton);
      bottomLeftPanel.add(updateTruckButton);
      bottomLeftPanel.add(openRouteURLButton);

      // topLeftPanel.setPreferredSize(new Dimension(leftPane.getWidth(), (int)
      // (leftPane.getHeight() * 0.15)));
      // middleLeftPanel.setPreferredSize(new Dimension(leftPane.getWidth(), (int)
      // (leftPane.getHeight() * 0.5)));
      // bottomLeftPanel.setPreferredSize(new Dimension(leftPane.getWidth(), (int)
      // (leftPane.getHeight() * 0.35)));

      // Add sections to left pane
      leftPane.add(topLeftPanel);
      leftPane.add(middleLeftPanel);
      leftPane.add(bottomLeftPanel);

      // Set the preferred sizes of the panels

      // Add panes to main window
      add(leftPane, BorderLayout.WEST);
      add(rightPane, BorderLayout.CENTER);

      // Set initial truck info table
      DefaultTableModel truckInfoTableModel = new DefaultTableModel();
      truckInfoTableModel.addColumn("Label");
      truckInfoTableModel.addColumn("Value");
      truckInfoTable.setModel(truckInfoTableModel);

      // Add action listener to truck dropdown
      truckDropdown.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String selectedTruck = (String) truckDropdown.getSelectedItem();
            if (selectedTruck != null) {
               // Get the truck object from the warehouse object
               for (Truck truck : warehouse.getTrucks()) {
                  if (truck.getName().equals(selectedTruck)) {
                     // Update truck info table
                     updateTruckInfoTable(truck);
                  }
               }
            }
         }
      });

      addTruckButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // Generate random truck data
            Random random = new Random();
            String truckName = "Truck " + (warehouse.getFleet().size() + 1);
            String numberPlate = "ABC" + (random.nextInt(900) + 100);
            String[] goods = { "Item 1", "Item 2", "Item 3" };
            double singleDriveTime = random.nextDouble() * 10;
            double drivingSpeed = random.nextDouble() * 100;
            double fuelEfficiency = random.nextDouble() * 20;
            double currentLongitude = -1 * (random.nextDouble() * (112.4 - 111.8) + 111.8);
            double currentLatitude = random.nextDouble() * (33.6 - 33.4409) + 33.4409;
            double[] routeBegin = { -1 * (random.nextDouble() * (112.4 - 111.8) + 111.8),
                  random.nextDouble() * (33.6 - 33.4409) + 33.4409 };

            double[] routeEnd = { -1 * (random.nextDouble() * (112.4 - 111.8) + 111.8),
                  random.nextDouble() * (33.6 - 33.4409) + 33.4409 };
            System.out.println((routeBegin[1] + "," + routeBegin[0] + "\n" + routeEnd[1] + "," + routeEnd[0]));
            // System.out.println(GoogleMaps.getPolyline(routeBegin[1], routeBegin[0],
            // routeEnd[1], routeEnd[0]));
            // try {
            // System.out.println(
            // URLEncoder.encode(GoogleMaps.getPolyline(routeBegin[1], routeBegin[0],
            // routeEnd[1], routeEnd[0]),
            // StandardCharsets.UTF_8.toString()));

            // } catch (UnsupportedEncodingException e1) {
            // // TODO Auto-generated catch block
            // e1.printStackTrace();
            // }

            try {
               getImageMap(GoogleMaps.getPolyline(routeBegin[1], routeBegin[0], routeEnd[1], routeEnd[0]));
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }

            // Create the truck object
            Truck truck = new Truck(truckName, numberPlate, goods, singleDriveTime, currentLongitude, currentLatitude,
                  routeBegin, routeEnd,
                  drivingSpeed, fuelEfficiency);

            // Add the truck to the warehouse's fleet
            warehouse.getFleet().add(truck);

            // Update the truck dropdown and truck info table
            updateTruckDropdown();
            updateTruckInfoTable(truck);
         }
      });

      // Create a new Warehouse object and feed it with random data
      warehouse = new Warehouse("Warehouse", 1234, 0.0, 0.0);
      generateRandomData();

      // Display the window
      setVisible(true);
   }

   public void getImageMap(String polyline) throws IOException {
      String urlCall = "https://api.mapbox.com/styles/v1/aryankeluskar/clpl75rqc009101px48ya3a8r/static/pin-s+F00(-112.0930157100277,33.50053232723088),path-5+f44-0.5("
            + URLEncoder.encode(polyline, StandardCharsets.UTF_8)
            + ")/auto/500x300?access_token=pk.eyJ1IjoiYXJ5YW5rZWx1c2thciIsImEiOiJjbHBpMTczMjEwYnZsMmxxeDF6a2I0bDVoIn0.2-YAM-tj9acKRvWMC5ewxA";
      saveImage(urlCall, "map.png");
   }

   public static void saveImage(String imageUrl, String destinationFile) throws IOException {
      URL url = new URL(imageUrl);
      InputStream is = url.openStream();
      OutputStream os = new FileOutputStream(destinationFile);

      byte[] b = new byte[2048];
      int length;

      while ((length = is.read(b)) != -1) {
         os.write(b, 0, length);
      }

      is.close();
      os.close();
   }

   private void generateRandomData() {
      Random random = new Random();

      // Generate random warehouse data
      String warehouseName = "Warehouse " + (random.nextInt(100) + 1);

      // Set the generated data to the warehouse object
      warehouse.setName(warehouseName);

      // Update the GUI with the generated data
      warehouseNameLabel.setText(warehouse.getName());
      updateTruckDropdown();
   }

   private void updateTruckDropdown() {
      DefaultComboBoxModel<String> truckDropdownModel = new DefaultComboBoxModel<>();
      for (int i = 1; i <= warehouse.getFleet().size(); i++) {
         truckDropdownModel.addElement("Truck " + i);
      }
      truckDropdown.setModel(truckDropdownModel);

   }

   private void updateTruckInfoTable(Truck truck) {
      DefaultTableModel truckInfoTableModel = (DefaultTableModel) truckInfoTable.getModel();
      truckInfoTableModel.setRowCount(0);

      // Add truck info rows to the table
      truckInfoTableModel.addRow(new Object[] { "Number Plate", truck.getNumberPlate() });
      System.out.println("Row Title: Number Plate, Data: " + truck.getNumberPlate());

      truckInfoTableModel.addRow(new Object[] { "Goods", String.join(", ", truck.getGoods()) });
      System.out.println("Row Title: Goods, Data: " + String.join(", ", truck.getGoods()));

      truckInfoTableModel.addRow(new Object[] { "Total Drive Time", truck.getTotalDriveTime() });
      System.out.println("Row Title: Total Drive Time, Data: " + truck.getTotalDriveTime());

      truckInfoTableModel.addRow(
            new Object[] { "Current Location", truck.getCurrentLocation()[0] + ", " + truck.getCurrentLocation()[1] });
      System.out.println("Row Title: Current Location, Data: " + truck.getCurrentLocation()[0] + ", "
            + truck.getCurrentLocation()[1]);

      truckInfoTableModel.addRow(
            new Object[] { "Route Begin", GoogleMaps.getName(truck.getRouteBegin()[1], truck.getRouteBegin()[0]) });
      System.out.println("Row Title: Route Begin, Data: " + truck.getRouteBegin()[0] + ", " + truck.getRouteBegin()[1]);

      truckInfoTableModel
            .addRow(new Object[] { "Route End", GoogleMaps.getName(truck.getRouteEnd()[1], truck.getRouteEnd()[0]) });
      System.out.println("Row Title: Route End, Data: " + truck.getRouteEnd()[0] + ", " + truck.getRouteEnd()[1]);

      truckInfoTableModel.addRow(new Object[] { "Total Drive Time", truck.getTotalDriveTime() });
      System.out.println("Row Title: Total Drive Time, Data: " + truck.getTotalDriveTime());

      truckInfoTableModel.addRow(new Object[] { "Driving Speed", truck.getDrivingSpeed() });
      System.out.println("Row Title: Driving Speed, Data: " + truck.getDrivingSpeed());

      truckInfoTableModel.addRow(new Object[] { "Fuel Efficiency", truck.getFuelEfficiency() });
      System.out.println("Row Title: Fuel Efficiency, Data: " + truck.getFuelEfficiency());
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new WarehouseManagementWindow());
   }

}
