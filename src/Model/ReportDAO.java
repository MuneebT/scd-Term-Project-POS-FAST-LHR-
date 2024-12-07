package Model;

import java.sql.Connection;
import Connection.ConnectionConfigurator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

public class ReportDAO {

    public ReportDAO()
    {

    }
    public LinkedHashMap<String, Double> fetchData(String reportType, String timeRange) {
        LinkedHashMap<String, Double> data = new LinkedHashMap<>();
        String query = "";

        try (Connection conn = ConnectionConfigurator.getConnection()) {
            switch (reportType) {
                case "Sales":
                    query = generateSalesQuery(timeRange);
                    break;
                case "Remaining Stock":
                    query = generateRemainingStockQuery();
                    break;
                case "Profit":
                    query = generateProfitQuery(timeRange);
                    break;
            }

            try (PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                if ("Remaining Stock".equals(reportType)) {
                    // For Remaining Stock, no date formatting is needed
                    while (rs.next()) {
                        String category = rs.getString("Product Category");
                        double value = rs.getDouble("Quantity");
                        data.put(category, value);
                    }
                } else {

                    if ("Monthly".equals(timeRange)) {
                        addMissingMonths(data);
                    } else if ("Weekly".equals(timeRange)) {
                        addMissingWeekdays(data);
                    }
                    else if ("Today".equals(timeRange)) {
                        addMissingWeekdays(data);
                    }

                    // For Sales and Profit, handle dates
                    while (rs.next()) {
                        String key = "";
                        if("Today".equals(timeRange))
                        {
                            key = getWeekdayName(rs.getInt("Weekday"));
                            double value = rs.getDouble("Value");
                            //data.put(key, value);
                            data.replace(key,value);


                        }
                        if ("Yearly".equals(timeRange))
                        {
                            key = rs.getString("Year");
                            double value = rs.getDouble("Value");
                            data.put(key, value);
                        }
                        else if ("Monthly".equals(timeRange))
                        {
                            key = getMonthName(rs.getInt("Month"));
                            double value = rs.getDouble("Value");
                            //data.put(key, value);
                            data.replace(key,value);
                        }
                        else if ("Weekly".equals(timeRange))
                        {
                            key = getWeekdayName(rs.getInt("Weekday"));
                            double value = rs.getDouble("Value");
                            //data.put(key, value);
                            data.replace(key,value);
                        }


                    }
                    System.out.println("Data is now:"+data);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Add missing data points (e.g., months or weekdays with no data)


        return data;
    }

    // Generate Sales Query
    private String generateSalesQuery(String timeRange) {
        String dateCondition = getDateCondition(timeRange, "Sales");
        return "SELECT MONTH(DateTime) AS Month, WEEKDAY(DateTime) AS Weekday, YEAR(DateTime) AS Year, SUM(TotalBill) AS Value " +
                "FROM Invoice " +
                "WHERE " + dateCondition;
    }

    // Generate Remaining Stock Query
    private String generateRemainingStockQuery() {
        return "SELECT ProductCategory AS 'Product Category', SUM(ProductQuantity) AS Quantity " +
                "FROM Inventory " +
                "GROUP BY ProductCategory";
    }

    // Generate Profit Query
    private String generateProfitQuery(String timeRange) {
        String dateCondition = getDateCondition(timeRange, "Profit");
        return "SELECT MONTH(DateTime) AS Month, WEEKDAY(DateTime) AS Weekday, YEAR(DateTime) AS Year, SUM(Sale.TotalPrice - (Inventory.CostPrice * Sale.Quantity)) AS Value " +
                "FROM Sale " +
                "JOIN Inventory ON Sale.ProdId = Inventory.ProductID " +
                "JOIN Invoice ON Sale.InvoiceNumber = Invoice.InvoiceID " +
                "WHERE " + dateCondition;
    }

    // Determine Date Condition Based on Time Range and Report Type
    private String getDateCondition(String timeRange, String reportType) {
        switch (timeRange) {
            case "Today":
                return "DATE(DateTime) = CURRENT_DATE  GROUP BY YEAR(DateTime), MONTH(DateTime), WEEKDAY(DateTime)";
            case "Weekly":
                return "YEARWEEK(DateTime, 1) = YEARWEEK(CURRENT_DATE, 1) GROUP BY WEEKDAY(DateTime) ORDER BY WEEKDAY(DateTime) ASC;";
            case "Monthly":
                return "YEAR(DateTime) = YEAR(CURRENT_DATE) GROUP BY MONTH(DateTime) ORDER BY MONTH(DateTime) ASC;\n;";
            case "Yearly":
                return "YEAR(DateTime) BETWEEN YEAR(CURRENT_DATE) - 4 AND YEAR(CURRENT_DATE) GROUP BY YEAR(DateTime) ;";
            default: // "Custom Range" or others
                return "1=1"; // No specific condition, for demonstration purposes
        }
    }

    // Helper to get the month name from a number
    private String getMonthName(int month) {
        switch (month) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "";
        }
    }

    // Helper to get the weekday name from a number
    private String getWeekdayName(int weekday) {
        switch (weekday) {

            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            case 7: return "Sunday";
            default: return "";
        }
    }

    // Add missing months to the data (for the current year)
    private void addMissingMonths(LinkedHashMap<String, Double> data) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        System.out.println("before input"+data);
        for (String month : months) {
            System.out.println("Curr month to be added:"+month);
            data.put(month, 0.0);
        }
        System.out.println("after default input:"+data);
    }

    // Add missing weekdays to the data (for the current week)
    private void addMissingWeekdays(LinkedHashMap<String, Double> data) {
        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
        for (String weekday : weekdays) {
            data.put(weekday, 0.0);
        }
        System.out.println(data);
    }
}
