package View;

import Connection.ConnectionConfigurator;
import Controller.ReportController;

import Model.Branch;
import Model.BranchDAO;
import Model.LoggedEmp;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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


        BranchDAO branchDAO = new BranchDAO();
        LinkedList<Branch> branches = branchDAO.getAllBranches();

// Create the branch combo box and populate it
        JComboBox<String> branchDropdown = new JComboBox<>();
        Branch branch;
        for (int i=0;i<branches.size();i++) {
            branch=branches.get(i);
            branchDropdown.addItem(branch.getBranchID() + " - " + branch.getBranchName());
        }

// Add the branch dropdown to the controls panel
        controls.add(new JLabel("Select Branch:"));
        controls.add(branchDropdown);
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

            String selectedBranch = (String) branchDropdown.getSelectedItem();

            // Parse the branch ID from the selected item
            String branchId = selectedBranch.split(" - ")[0];  // Get the "BRANCH_ID_HERE" part

            System.out.println("Selected Branch ID: " + branchId);

            // Fetch Data from Database
            ReportController controller = new ReportController();
            LinkedHashMap<String, Double> data = controller.fetchData(reportType, timeRange,branchId);

            LinkedHashMap<String, Double> newData=new LinkedHashMap<>();

            JPanel chartPanel = null;


            String dayName = LocalDate.now().getDayOfWeek().name();
            String year=String.valueOf(LocalDate.now().getYear());
            String month=LocalDate.now().getMonth().name();

            // Iterate through the original map
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();

                // If the key is "Today", replace it with "Today - DayName"
                if (dayName.toLowerCase().equals(key.toLowerCase())) {
                    key = "Today - " + dayName;
                }

                else if(year.equals(key))
                {
                    key="This Year - "+year;
                }
                else if (month.toLowerCase().equals(key.toLowerCase())) {
                    key = "This Month - " + month;
                }

                // Put the updated key-value pair into the new map
                newData.put(key, value);
            }
            data=newData;

            if (data != null && !data.isEmpty()) {
                if (reportType.equals("Remaining Stock")) {
                    dataTable.setModel(new DefaultTableModel(new Object[]{"Category", "Quantity"}, 0));
                    chartPanel = ChartGenerator.createBarChart(reportType, "Category", "Quantity", data);
                } else if (reportType.equals("Sales") && timeRange.equals("Yearly"))
                {


                    dataTable.setModel(new DefaultTableModel(new Object[]{"Year", "Sale"}, 0));
                    chartPanel = ChartGenerator.createBarChart(reportType, "Year", "Sale", data);
                }
                else if (reportType.equals("Sales") && timeRange.equals("Monthly")) {
                    dataTable.setModel(new DefaultTableModel(new Object[]{"Month", "Sale"}, 0));
                    chartPanel = ChartGenerator.createBarChart(reportType, "Month", "Sale", data);
                } else if (reportType.equals("Sales") && timeRange.equals("Weekly")) {
                    dataTable.setModel(new DefaultTableModel(new Object[]{"Day", "Sale"}, 0));
                    chartPanel = ChartGenerator.createBarChart(reportType, "Day", "Sale", data);
                } else if (reportType.equals("Sales") && timeRange.equals("Today")) {
                    dataTable.setModel(new DefaultTableModel(new Object[]{"Time", "Sale"}, 0));
                    chartPanel = ChartGenerator.createBarChart(reportType, "Time", "Sale", data);
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

                else if (reportType.equals("Profit") && timeRange.equals("Today")) {
                    dataTable.setModel(new DefaultTableModel(new Object[]{"Day", "Profit"}, 0));
                    chartPanel = ChartGenerator.createBarChart(reportType, "Day", "Profit", data);
                }
            } else {
                // Handle case when no data is returned
                JOptionPane.showMessageDialog(frame, "No data available for the selected report and time range.", "No Data", JOptionPane.WARNING_MESSAGE);
            }

            // Update Table with Data
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setRowCount(0);  // Clear existing rows
            if (data != null) {
                for (Map.Entry<String, Double> entry : data.entrySet()) {
                    model.addRow(new Object[]{entry.getKey(), String.format("%.2f", entry.getValue())});
                }
            }

            // Generate Chart and add it to panel
            chartArea.removeAll();
            if (chartPanel != null) {
                chartArea.add(chartPanel, BorderLayout.CENTER);
            }
            chartArea.add(scrollPane, BorderLayout.EAST);
            chartArea.revalidate();
            chartArea.repaint();
        });



        // Back Button Action
        backButton.addActionListener(e -> {
            frame.dispose();  // Close current frame
            LoggedEmp loggedEmp=LoggedEmp.getInstance();
            if(loggedEmp.getDesignation().equals("Branch Manager"))
            {
                new BMDashboardView();
            }
            else
            {
                new SADashboardView();
            }
        });

        frame.add(reportPanel);
        frame.setVisible(true);
    }

    // Method to Display the Previous Screen
    private static void showPreviousScreen() {

        new SADashboardView();

    }

}
