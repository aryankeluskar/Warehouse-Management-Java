import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarehouseManagementWindow extends JFrame {
   private JLabel warehouseLabel;
   private JLabel warehouseNameLabel;
   private JComboBox<String> truckDropdown;
   private JTextArea truckInfoTextArea;
   private JButton addTruckButton;
   private JButton deleteTruckButton;
   private JButton updateTruckButton;
   private JButton openRouteURLButton;

   public WarehouseManagementWindow() {
      // Set window properties
      setTitle("Warehouse Management");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new BorderLayout());
      setSize(800, 600);

      // Create right pane with website
      JPanel rightPane = new JPanel();
      rightPane.setPreferredSize(new Dimension(getWidth() * 80 / 100, getHeight()));
      rightPane.setBackground(Color.WHITE);
      rightPane.add(new JLabel("Website"));

      // Create left pane with three equal height sections
      JPanel leftPane = new JPanel();
      leftPane.setLayout(new GridLayout(3, 1));

      // Top left section for warehouse labels
      JPanel topLeftPanel = new JPanel();
      topLeftPanel.setLayout(new FlowLayout());
      warehouseLabel = new JLabel("Warehouse");
      warehouseNameLabel = new JLabel("Warehouse Name");
      topLeftPanel.add(warehouseLabel);
      topLeftPanel.add(warehouseNameLabel);

      // Middle left section for truck dropdown and info
      JPanel middleLeftPanel = new JPanel();
      middleLeftPanel.setLayout(new BorderLayout());
      truckDropdown = new JComboBox<>();
      truckInfoTextArea = new JTextArea();
      middleLeftPanel.add(truckDropdown, BorderLayout.NORTH);
      middleLeftPanel.add(new JScrollPane(truckInfoTextArea), BorderLayout.CENTER);

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

      // Set initial truck info
      truckInfoTextArea.setText("Select a truck from the dropdown to view its info.");

      // Add action listener to truck dropdown
      truckDropdown.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String selectedTruck = (String) truckDropdown.getSelectedItem();
            if (selectedTruck != null) {
               // Get the truck object from the warehouse object
               for (Truck truck : Warehouse.getTrucks()) {
                  if (truck.getName().equals(selectedTruck)) {
                     // Display truck info in text area
                     truckInfoTextArea.setText(getTruckInfoString(truck));
                  }
               }
            }
         }
      });

      // Display the window
      setVisible(true);
   }

   private String getTruckInfoString(Truck truck) {
      // Create a string representation of the truck info
      // You can format it as per your requirement
      // Replace the placeholders with the actual truck info


      String info = "Number Plate: " + truck.getNumberPlate() + "\n" +
            "Goods: " + String.join(", ", truck.getGoods()) + "\n" +
            "Total Drive Time: " + truck.getTotalDriveTime() + "\n" +
            "Current Location: " + truck.getCurrentLocation()[0] + ", " + truck.getCurrentLocation()[1] + "\n" +
            "Route Begin: " + truck.getRouteBegin()[0] + ", " + truck.getRouteBegin()[1] + "\n" +
            "Route End: " + truck.getRouteEnd()[0] + ", " + truck.getRouteEnd()[1] + "\n" +
            "Total Drive Time: " + truck.getTotalDriveTime() + "\n" +
            "Driving Speed: " + truck.getDrivingSpeed() + "\n" +
            "Fuel Efficiency: " + truck.getFuelEfficiency() + "\n" +
            "Tampering: " + truck.isTampering() + "\n" +
            "Collisions: " + truck.isCollisions();
      return info;
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new WarehouseManagementWindow());
   }
}

