package Controller;

import Model.ReportDAO;

import java.util.LinkedHashMap;

public class ReportController {

    public LinkedHashMap<String, Double> fetchData(String reportType, String timeRange,String branchId)
    {
        ReportDAO dao=new ReportDAO();
        return dao.fetchData(reportType,timeRange,branchId);
    }


}