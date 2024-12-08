package View;

import Connection.ConnectionConfigurator;
import Controller.ReportController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportView {

    public ReportView()
    {
        createAndShowGUI();
    }



    // Chart Generator Class
    public static class ChartGenerator {
        public static JPanel createBarChart(String title, String xAxisLabel, String yAxisLabel, Map<String, Double> data) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            double maxValue = 0;
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                dataset.addValue(entry.getValue(), title, entry.getKey());
                if (entry.getValue() > maxValue) {
                    maxValue = entry.getValue();
                }
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    title,        // Chart title
                    xAxisLabel,   // X-axis label
                    yAxisLabel,   // Y-axis label
                    dataset,      // Data
                    PlotOrientation.VERTICAL,
                    true,         // Include legend
                    true,         // Tooltips
                    false         // URLs
            );

            // Access CategoryPlot and set the Y-axis range
            CategoryPlot plot = chart.getCategoryPlot();
            ValueAxis yAxis = plot.getRangeAxis();
            yAxis.setRange(0, maxValue * 2); // Set the range to double the max value

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(600, 400));  // Set chart panel size
            return chartPanel;
        }
    }

    // Main GUI Method
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("View Reports");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new BorderLayout(10, 10));
        reportPanel.setBackground(new Color(245, 245, 245));

        // Panel for Dropdowns and Buttons
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Dropdowns for Report Selection
        JComboBox<String> reportTypeDropdown = new JComboBox<>(new String[]{"Sales", "Remaining Stock", "Profit"});
        JComboBox<String> timeRangeDropdown = new JComboBox<>(new String[]{"Today", "Weekly", "Monthly", "Yearly", "Custom Range"});
        JButton generateButton = new JButton("Generate Report");
        JButton backButton = new JButton("Back");

        controls.add(new JLabel("Report Type:"));
        controls.add(reportTypeDropdown);
        controls.add(new JLabel("Time Range:"));
        controls.add(timeRangeDropdown);
        controls.add(generateButton);
        controls.add(backButton);

        reportPanel.add(controls, BorderLayout.NORTH);

        // Panel for displaying chart and data
        JPanel chartArea = new JPanel();
        chartArea.setLayout(new BorderLayout(10, 10));
        reportPanel.add(chartArea, BorderLayout.CENTER);

        // Table for displaying data
        JTable dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        chartArea.add(scrollPane, BorderLayout.EAST);
        dataTable.setEnabled(false);

        generateButton.addActionListener(e -> {
            String reportType = (String) reportTypeDropdown.getSelectedItem();
            String timeRange = (String) timeRangeDropdown.getSelectedItem();

            // Fetch Data from Database
            ReportController controller = new ReportController();

            LinkedHashMap<String, Double> data = controller.fetchData(reportType, timeRange);

            JPanel chartPanel = null;
            if (reportType.equals("Remaining Stock")) {
                dataTable.setModel(new DefaultTableModel(new Object[]{"Category", "Quantity"}, 0));
                chartPanel = ChartGenerator.createBarChart(reportType, "Category", "Quantity", data);
            } else if (reportType.equals("Sales") && timeRange.equals("Yearly")) {
                dataTable.setModel(new DefaultTableModel(new Object[]{"Year", "Sale"}, 0));
                chartPanel = ChartGenerator.createBarChart(reportType, "Year", "Sale", data);
            } else if (reportType.equals("Sales") && timeRange.equals("Monthly")) {
                dataTable.setModel(new DefaultTableModel(new Object[]{"Month", "Sale"}, 0));
                chartPanel = ChartGenerator.createBarChart(reportType, "Month", "Sale", data);
            } else if (reportType.equals("Sales") && timeRange.equals("Weekly")) {
                dataTable.setModel(new DefaultTableModel(new Object[]{"Day", "Sale"}, 0));
                chartPanel = ChartGenerator.createBarChart(reportType, "Day", "Sale", data);
            } else if (reportType.equals("Profit") && timeRange.equals("Yearly")) {
                dataTable.setModel(new DefaultTableModel(new Object[]{"Year", "Profit"}, 0));
                chartPanel = ChartGenerator.createBarChart(reportType, "Year", "Profit", data);
            } else if (reportType.equals("Profit") && timeRange.equals("Monthly")) {
                dataTable.setModel(new DefaultTableModel(new Object[]{"Month", "Profit"}, 0));
                chartPanel = ChartGenerator.createBarChart(reportType, "Month", "Profit", data);
            } else if (reportType.equals("Profit") && timeRange.equals("Weekly")) {
                dataTable.setModel(new DefaultTableModel(new Object[]{"Day", "Profit"}, 0));
                chartPanel = ChartGenerator.createBarChart(reportType, "Day", "Profit", data);
            }

            // Update Table with Data
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setRowCount(0);  // Clear existing rows
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                model.addRow(new Object[]{entry.getKey(), String.format("%.2f", entry.getValue())});
            }

            // Generate Chart and add it to panel
            chartArea.removeAll();
            chartArea.add(chartPanel, BorderLayout.CENTER);
            chartArea.add(scrollPane, BorderLayout.EAST);
            chartArea.revalidate();
            chartArea.repaint();
        });

        // Back Button Action
        backButton.addActionListener(e -> {
            frame.dispose();  // Close current frame
            showPreviousScreen();  // Call method to display the previous screen
        });

        frame.add(reportPanel);
        frame.setVisible(true);
    }

    // Method to Display the Previous Screen
    private static void showPreviousScreen() {

        System.out.println("Back button clicked. Returning to the previous screen...");

    }
}
