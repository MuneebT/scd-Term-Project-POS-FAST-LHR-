package Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.swing.*;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import Connection.ConnectionConfigurator;

public class BranchManagementModel {
    private static BranchManagementModel bmm = null;

    private BranchManagementModel() {
        bmm = null;
    }

    public static BranchManagementModel getInstance() {
        if (bmm == null) {
            bmm = new BranchManagementModel();
        }
        return bmm;
    }

}