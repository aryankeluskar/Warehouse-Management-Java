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
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
   private Warehouse warehouse;
   public double wrlongitude, wrlatitude;
   public JPanel rightPane;
   public double[] routeBegin, routeEnd;

   /**
    * Constructor for the WarehouseManagementWindow class
    */

   public WarehouseManagementWindow() {

      Random random = new Random();

      wrlongitude = -1 * (random.nextDouble() * (112.4 - 111.8) + 111.8);
      wrlatitude = random.nextDouble() * (33.6 - 33.4409) + 33.4409;

      // Create Warehouse object
      warehouse = new Warehouse("Warehouse Name", 123, wrlongitude, wrlatitude);

      // Set window properties

      setTitle("Warehouse Management");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new BorderLayout());
      setSize(1200, 600);

      // Create right pane with image of map.png which fills the right panel. put
      // width and height of panel as 1000 and 600
      JPanel rightPane = new JPanel();
      rightPane.setBackground(Color.WHITE);
      rightPane.setLayout(new BorderLayout());
      ImageIcon mapImage = new ImageIcon("map.png");
      JLabel mapLabel = new JLabel(mapImage);
      rightPane.add(mapLabel, BorderLayout.CENTER);

      // Set the size of the right pane
      // rightPane.setPreferredSize(new Dimension(1000, 600));

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

      // Add sections to left pane
      leftPane.add(topLeftPanel);
      leftPane.add(middleLeftPanel);
      leftPane.add(bottomLeftPanel);

      // Add panes to main window
      add(leftPane, BorderLayout.WEST);
      add(rightPane, BorderLayout.CENTER);

      // Set initial truck info table
      DefaultTableModel truckInfoTableModel = new DefaultTableModel();
      truckInfoTableModel.addColumn("Label");
      truckInfoTableModel.addColumn("Value");
      truckInfoTable.setModel(truckInfoTableModel);

      openRouteURLButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String selectedTruck = (String) truckDropdown.getSelectedItem();
            if (selectedTruck != null) {
               // Get the truck object from the warehouse object
               for (Truck truck : warehouse.getTrucks()) {
                  if (truck.getName().equals(selectedTruck)) {
                     GoogleMaps.getPolyline(truck.routeBegin[1], truck.routeBegin[0], truck.routeEnd[1],
                           truck.routeEnd[0]);
                  }
               }
            }

         }

      });

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
                     try {
                        updateTruckInfoTable(truck);
                     } catch (IOException e1) {
                        e1.printStackTrace();
                     }
                     // ImageIcon mapImage = new ImageIcon("map"+truck.getNumberPlate()+".png");
                     // JLabel mapLabel = new JLabel(mapImage);
                     // rightPane.add(mapLabel, BorderLayout.CENTER);
                     // rightPane.revalidate();
                     // rightPane.repaint();
                     // rightPane.removeAll();
                     rightPane.removeAll();
                     JLabel mapboxx = null;
                     File f = new File("map" + truck.getNumberPlate() + "new.png");
                     mapboxx = new JLabel(new ImageIcon("map" + truck.getNumberPlate() + ".png"));
                     if(f.exists() && !f.isDirectory()){
                        mapboxx = new JLabel(new ImageIcon("map" + truck.getNumberPlate() + "new.png"));
                     }
                     rightPane.add(mapboxx, BorderLayout.CENTER);
                     rightPane.revalidate();
                     rightPane.repaint();

                     // rightPane.add(leftPane, BorderLayout.EAST);

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
            double[] routeBegin = { wrlongitude, wrlatitude };

            double[] routeEnd = { -1 * (random.nextDouble() * (112.4 - 111.8) + 111.8),
                  random.nextDouble() * (33.6 - 33.4409) + 33.4409 };
            System.out.println((routeBegin[1] + "," + routeBegin[0] + "\n" + routeEnd[1] + "," + routeEnd[0]));

            // Create the truck object
            String currPolyline = GoogleMaps.getPolyline(routeBegin[1], routeBegin[0], routeEnd[1], routeEnd[0]);
            try {
               getImageMap(currPolyline, wrlongitude, wrlatitude, numberPlate);
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
            Truck truck = new Truck(truckName, numberPlate, goods, singleDriveTime, currentLongitude, currentLatitude,
                  routeBegin, routeEnd,
                  drivingSpeed, fuelEfficiency, currPolyline);

            // Add the truck to the warehouse's fleet
            warehouse.getFleet().add(truck);

            // Update the truck dropdown and truck info table
            updateTruckDropdown();
            try {
               updateTruckInfoTable(truck);
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }
      });

      deleteTruckButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String selectedTruck = (String) truckDropdown.getSelectedItem();
            if (selectedTruck != null) {
               // Get the truck object from the warehouse object
               for (Truck truck : warehouse.getTrucks()) {
                  if (truck.getName().equals(selectedTruck)) {
                     // Remove the truck from the warehouse's fleet
                     warehouse.getFleet().remove(truck);

                     // Update the truck dropdown and truck info table
                     updateTruckDropdown();
                     try {
                        updateTruckInfoTable(truck);
                     } catch (IOException e1) {
                        e1.printStackTrace();
                     }
                  }
               }
            }
         }
      });

      updateTruckButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String selectedTruck = (String) truckDropdown.getSelectedItem();
            if (selectedTruck != null) {
               // Get the truck object from the warehouse object
               for (Truck truck : warehouse.getTrucks()) {
                  if (truck.getName().equals(selectedTruck)) {
                     // Update the truck attributes based on the textboxes
                     System.out.println(truckInfoTableModel.getValueAt(0, 1));

                     // Call getPolyline from GoogleMaps class
                     String currPolyline = GoogleMaps.getPolyline(truckInfoTableModel.getValueAt(4, 1).toString(),
                           truckInfoTableModel.getValueAt(5, 1).toString());
                     truck.setPolylineString(currPolyline);

                     // Call getImageMap to update the image of the map
                     try {
                        getImageMap(currPolyline, wrlongitude, wrlatitude, truck.getNumberPlate()+"new");
                     } catch (IOException e1) {
                        e1.printStackTrace();
                     }

                     // Update the truck dropdown and truck info table
                     updateTruckDropdown();
                     try {
                        updateTruckInfoTable(truck);
                     } catch (IOException e1) {
                        e1.printStackTrace();
                     }

                     // rightPane.removeAll();
                     // JLabel mapboxx = null;
                     JLabel mapboxx2 = new JLabel(new ImageIcon("map" + truck.getNumberPlate() + "new.png"));
                     rightPane.add(mapboxx2, BorderLayout.CENTER);
                     rightPane.revalidate();
                     rightPane.repaint();
                  }
               }
            }
         }
      });

      openRouteURLButton.addActionListener(new ActionListener() {

         @SuppressWarnings("deprecation")
         @Override
         public void actionPerformed(ActionEvent e) {
            String selectedTruck = (String) truckDropdown.getSelectedItem();
            if (selectedTruck != null) {
               // Get the truck object from the warehouse object
               for (Truck truck : warehouse.getTrucks()) {
                  if (truck.getName().equals(selectedTruck)) {
                     // Open the route URL in the default browser
                     try {
                        Desktop.getDesktop().browse(
                              new URL(
                                    "https://www.google.com/maps/dir/33.6354739,-112.26867/" + truck.getRouteBegin()[1]
                                          + ","
                                          + truck.getRouteBegin()[0] + "/@" + truck.getRouteEnd()[1] + ","
                                          + truck.getRouteEnd()[0] + ",12.67z/")
                                    .toURI());
                        System.out.println(
                              "https://www.google.com/maps/dir/33.6354739,-112.26867/" + truck.getRouteBegin()[1] + ","
                                    + truck.getRouteBegin()[0] + "/@" + truck.getRouteEnd()[1] + ","
                                    + truck.getRouteEnd()[0] + ",12.67z/");
                     } catch (Exception e1) {
                        e1.printStackTrace();
                     }
                  }
               }
               System.out.println(truckInfoTableModel.getValueAt(1, 1));
            }
         }

      });

      // Create a new Warehouse object and feed it with random data
      warehouse = new Warehouse("Warehouse", 1234, 0.0, 0.0);
      generateRandomData();

      // Display the window
      setVisible(true);
   }

   /**
    * 
    * @param polyline - String - string representation of the encoded polyline
    * @param wrlong   - double - longitude of the warehouse
    * @param wrlati   - double - latitude of the warehouse
    * @param lic      - String - license plate of the truck
    * @throws IOException
    */

   public void getImageMap(String polyline, double wrlong, double wrlati, String lic) throws IOException {

      String urlCall = "https://api.mapbox.com/styles/v1/aryankeluskar/clpl75rqc009101px48ya3a8r/static/pin-s+F00("
            + wrlong + "," + wrlati + "),path-5+f44-0.5("
            + URLEncoder.encode(polyline, StandardCharsets.UTF_8)
            + ")/auto/550x550?access_token=" + APIKeys.getMapBoxAPI();
      System.out.println(urlCall);
      saveImage(urlCall, "map" + lic + ".png");
   }

   /**
    * @param imageUrl        - String - the url of the image to be saved
    * @param destinationFile - String - the file path where the image will be saved
    * @throws IOException
    */
   public static void saveImage(String imageUrl, String destinationFile) throws IOException {
      @SuppressWarnings("deprecation")
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

   /**
    * Generates random data for the warehouse and updates the GUI
    */

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

   /**
    * Updates the truck dropdown with the trucks in the warehouse's fleet
    */
   private void updateTruckDropdown() {
      DefaultComboBoxModel<String> truckDropdownModel = new DefaultComboBoxModel<>();
      for (int i = 1; i <= warehouse.getFleet().size(); i++) {
         truckDropdownModel.addElement("Truck " + i);
      }
      truckDropdown.setModel(truckDropdownModel);

   }

   /**
    * Updates the truck info table with the data from the given truck
    * 
    * @param truck - Truck - the truck object to get the data from
    * @throws IOException
    */
   private void updateTruckInfoTable(Truck truck) throws IOException {
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
