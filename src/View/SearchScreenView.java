package View;

import Controller.BranchManagementController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class SearchScreenView extends JFrame {
    private JTable table;
    private BranchManagementController bmc = new BranchManagementController();
    private JScrollPane sp;
    private JButton btnsearch;

    private JComboBox<String> citynames, branchnames;

    public SearchScreenView() {
        setTitle("Search Screen");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLayout(null);  // Using null layout

        // Set up search field combo box for Branch Names
        LinkedList<String> branchnamedata = bmc.return_list_of_branch_names();
        String[] data = copy_data(branchnamedata);
        branchnames = new JComboBox<>(data);
        branchnames.setForeground(Color.GRAY);
        JScrollPane sp1 = new JScrollPane(branchnames);
        sp1.setBounds(20, 20, 200, 30);

        // Set up search field combo box for City Names
        LinkedList<String> cities = bmc.return_list_of_branchcity_names();
        String[] cityname = copy_data(cities);
        citynames = new JComboBox<>(cityname);
        citynames.setForeground(Color.GRAY);
        JScrollPane cityscroll = new JScrollPane(citynames);
        cityscroll.setBounds(550, 20, 200, 30);

        // Set up the table model and table
        String[] fields = {"ID", "Name", "City", "Status", "Address", "PhoneNo", "No of Employees"};
        Object[][] data1 = bmc.return_object_Array(); // Initial data
        table = new JTable(new DefaultTableModel(data1, fields));
        sp = new JScrollPane(table);
        sp.setBounds(0, 70, getWidth(), getHeight());

        // Search button settings
        btnsearch = new JButton("Search");
        btnsearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnsearch.setForeground(Color.GRAY);
        btnsearch.setBounds(350, 20, 100, 30);
        btnsearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String branchname = branchnames.getSelectedItem().toString();
                String city = citynames.getSelectedItem().toString();

                Object[][] updatedData = bmc.return_object_Array(branchname, city);

                reloadTable(updatedData);
            }
        });

        // Add components to the frame
        add(sp1);
        add(cityscroll);
        add(btnsearch);
        add(sp);

        setVisible(true);
    }

    // Reload table with new data
    private void reloadTable(Object[][] data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(data, new Object[]{"ID", "Name", "City", "Status", "Address", "PhoneNo", "No of Employees"});
    }

    private String[] copy_data(LinkedList<String> data) {
        String[] copieddata = new String[data.size()+1];
        copieddata[0]=" ";
        for (int i = 0; i < data.size(); i++) {
            copieddata[i+1] = data.get(i);
        }
        return copieddata;
    }

    public static void main(String[] args) {
        new SearchScreenView();
    }
}
