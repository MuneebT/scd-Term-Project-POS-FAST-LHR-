package test.Model;

import Model.ReportDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;


import java.sql.*;
import java.util.LinkedHashMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReportDAOTest {

    @InjectMocks
    private ReportDAO reportDAO;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchData_Sales_Monthly() throws Exception {
        String reportType = "Sales";
        String timeRange = "Monthly";
        String branchID = "1";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        when(mockResultSet.getString("Month")).thenReturn("1");
        when(mockResultSet.getString("Year")).thenReturn("2024");
        when(mockResultSet.getDouble("Value")).thenReturn(1000.0);

        LinkedHashMap<String, Double> result = reportDAO.fetchData(reportType, timeRange, branchID);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("January"));
        assertEquals(1000.0, result.get("January"));

        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testFetchData_Profit_Yearly() throws Exception {
        String reportType = "Profit";
        String timeRange = "Yearly";
        String branchID = "1";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        when(mockResultSet.getString("Year")).thenReturn("2024");
        when(mockResultSet.getDouble("Value")).thenReturn(5000.0);

        LinkedHashMap<String, Double> result = reportDAO.fetchData(reportType, timeRange, branchID);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("2024"));
        assertEquals(5000.0, result.get("2024"));

        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testFetchData_RemainingStock() throws Exception {
        String reportType = "Remaining Stock";
        String timeRange = "Monthly";
        String branchID = "1";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        when(mockResultSet.getString("Product Category")).thenReturn("Electronics");
        when(mockResultSet.getDouble("Quantity")).thenReturn(150.0);

        LinkedHashMap<String, Double> result = reportDAO.fetchData(reportType, timeRange, branchID);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("Electronics"));
        assertEquals(150.0, result.get("Electronics"));

        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testFetchData_EmptyResult() throws Exception {
        String reportType = "Sales";
        String timeRange = "Monthly";
        String branchID = "1";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(false);

        LinkedHashMap<String, Double> result = reportDAO.fetchData(reportType, timeRange, branchID);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testFetchData_MissingDataForWeekday() throws Exception {
        String reportType = "Sales";
        String timeRange = "Weekly";
        String branchID = "1";

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("Weekday")).thenReturn(0);
        when(mockResultSet.getDouble("Value")).thenReturn(200.0);

        LinkedHashMap<String, Double> result = reportDAO.fetchData(reportType, timeRange, branchID);

        assertNotNull(result);
        assertTrue(result.containsKey("Monday"));
        assertEquals(200.0, result.get("Monday"));
        assertTrue(result.containsKey("Tuesday"));
        assertTrue(result.containsKey("Wednesday"));
        assertTrue(result.containsKey("Thursday"));
        assertTrue(result.containsKey("Friday"));
        assertTrue(result.containsKey("Saturday"));
        assertTrue(result.containsKey("Sunday"));

        verify(mockPreparedStatement).executeQuery();
    }
}

