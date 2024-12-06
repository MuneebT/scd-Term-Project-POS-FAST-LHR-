//package View;
//
//import Connection.ConnectionConfigurator;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.category.DefaultCategoryDataset;
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.sql.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ReportView {
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(ReportView::createAndShowGUI);
//    }
//
//    // Chart Generator Class
//    public static class ChartGenerator {
//        public static JPanel createBarChart(String title, String xAxisLabel, String yAxisLabel, Map<String, Double> data) {
//            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//            for (Map.Entry<String, Double> entry : data.entrySet()) {
//                dataset.addValue(entry.getValue(), title, entry.getKey());
//            }
//            JFreeChart chart = ChartFactory.createBarChart(
//                    title,
//                    xAxisLabel,
//                    yAxisLabel,
//                    dataset
//            );
//            ChartPanel chartPanel = new ChartPanel(chart);
//            chartPanel.setPreferredSize(new Dimension(600, 400));
//            return chartPanel;
//        }
//    }
//
//    // Main GUI Method
//    private static void createAndShowGUI() {
//        JFrame frame = new JFrame("Reports and Graphs");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1000, 700);
//
//        JPanel reportPanel = new JPanel();
//        reportPanel.setLayout(new BorderLayout(10, 10));
//        reportPanel.setBackground(new Color(245, 245, 245));
//
//        // Panel for Dropdowns and Buttons
//        JPanel controls = new JPanel();
//        controls.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
//
//        // Dropdowns for Report Selection
//        JComboBox<String> reportTypeDropdown = new JComboBox<>(new String[]{"Sales", "Remaining Stock", "Profit"});
//        JComboBox<String> timeRangeDropdown = new JComboBox<>(new String[]{"Today", "Weekly", "Monthly", "Yearly", "Custom Range"});
//        JButton generateButton = new JButton("Generate Report");
//
//        controls.add(new JLabel("Report Type:"));
//        controls.add(reportTypeDropdown);
//        controls.add(new JLabel("Time Range:"));
//        controls.add(timeRangeDropdown);
//        controls.add(generateButton);
//
//        reportPanel.add(controls, BorderLayout.NORTH);
//
//        // Panel for displaying chart and data
//        JPanel chartArea = new JPanel();
//        chartArea.setLayout(new BorderLayout(10, 10));
//        reportPanel.add(chartArea, BorderLayout.CENTER);
//
//        // Table for displaying data
//        JTable dataTable = new JTable();
//        JScrollPane scrollPane = new JScrollPane(dataTable);
//        chartArea.add(scrollPane, BorderLayout.EAST);
//        dataTable.setModel(new DefaultTableModel(new Object[]{"Date", "Value"}, 0));
//        dataTable.setEnabled(false);
//
//        generateButton.addActionListener(e -> {
//            String reportType = (String) reportTypeDropdown.getSelectedItem();
//            String timeRange = (String) timeRangeDropdown.getSelectedItem();
//            HashMap<String, Double> data = fetchData(reportType, timeRange);
//
//            // Update Table with Data
//            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
//            model.setRowCount(0);
//            for (Map.Entry<String, Double> entry : data.entrySet()) {
//                model.addRow(new Object[]{entry.getKey(), String.format("%.2f", entry.getValue())});
//            }
//
//            // Generate Chart and add it to panel
//            JPanel chartPanel = ChartGenerator.createBarChart(reportType, "Date", "Value", data);
//            chartArea.removeAll();
//            chartArea.add(chartPanel, BorderLayout.CENTER);
//            chartArea.add(scrollPane, BorderLayout.EAST);
//            chartArea.revalidate();
//            chartArea.repaint();
//        });
//
//        frame.add(reportPanel);
//        frame.setVisible(true);
//    }
//
//    // Fetch Data from Database
//    private static HashMap<String, Double> fetchData(String reportType, String timeRange) {
//        HashMap<String, Double> data = new HashMap<>();
//        String query = "";
//
//        try (Connection conn = ConnectionConfigurator.getConnection()) {
//            switch (reportType) {
//                case "Sales":
//                    query = generateSalesQuery(timeRange);
//                    break;
//                case "Remaining Stock":
//                    query = generateRemainingStockQuery();
//                    break;
//                case "Profit":
//                    query = generateProfitQuery(timeRange);
//                    break;
//            }
//
//            try (PreparedStatement ps = conn.prepareStatement(query);
//                 ResultSet rs = ps.executeQuery()) {
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                while (rs.next()) {
//                    Date sqlDate = rs.getDate("Date");
//                    String formattedDate = dateFormat.format(sqlDate);
//                    double value = rs.getDouble("Value");
//                    data.put(formattedDate, value);
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return data;
//    }
//
//    // Generate Sales Query
//    private static String generateSalesQuery(String timeRange) {
//        String dateCondition = getDateCondition(timeRange);
//        return "SELECT DATE(DateTime) AS Date, SUM(TotalPrice) AS Value " +
//                "FROM Sale JOIN Invoice ON Sale.InvoiceNumber = Invoice.InvoiceID " +
//                "WHERE " + dateCondition +
//                " GROUP BY DATE(DateTime)";
//    }
//
//    // Generate Remaining Stock Query
//    private static String generateRemainingStockQuery() {
//        return "SELECT ProductName AS Date, ProductQuantity AS Value FROM Inventory";
//    }
//
//    // Generate Profit Query
//    private static String generateProfitQuery(String timeRange) {
//        String dateCondition = getDateCondition(timeRange);
//        return "SELECT DATE(DateTime) AS Date, SUM(Sale.TotalPrice - Inventory.CostPrice * Sale.Quantity) AS Value " +
//                "FROM Sale " +
//                "JOIN Inventory ON Sale.ProdId = Inventory.ProductID " +
//                "JOIN Invoice ON Sale.InvoiceNumber = Invoice.InvoiceID " +
//                "WHERE " + dateCondition +
//                " GROUP BY DATE(DateTime)";
//    }
//
//    // Determine Date Condition Based on Time Range
//    private static String getDateCondition(String timeRange) {
//        switch (timeRange) {
//            case "Today":
//                return "DATE(DateTime) = CURRENT_DATE";
//            case "Weekly":
//                return "YEARWEEK(DateTime, 1) = YEARWEEK(CURRENT_DATE, 1)";
//            case "Monthly":
//                return "MONTH(DateTime) = MONTH(CURRENT_DATE) AND YEAR(DateTime) = YEAR(CURRENT_DATE)";
//            case "Yearly":
//                return "YEAR(DateTime) = YEAR(CURRENT_DATE)";
//            default:
//                return "1=1";
//        }
//    }
//}
