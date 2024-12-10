package View;

import Connection.InternetConnectionChecker;
import Controller.CategoryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;

public class ManageCategoryView extends JFrame {

    private JTable table;
    private JScrollPane scrollPane;
    private CategoryController cc = new CategoryController();
private InternetConnectionChecker icc=new InternetConnectionChecker();
    public ManageCategoryView() {
        setTitle("Category Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setBounds(100, 100, 900, 600);

        // Create the 'Add' button
        JButton addButton = new JButton("Add Category");
        addButton.setBounds(700, 10, 150, 30);
        addButton.setBackground(Color.GREEN);
        addButton.setForeground(Color.WHITE);
        add(addButton);

        // Initialize the table with data
        initializeTable();

        // Action listener for the 'Add' button
        addButton.addActionListener(e -> {
            AddCategoryView addCategoryView = new AddCategoryView();
            addCategoryView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    refreshTableData(); // Refresh table after closing AddCategoryView
                }
            });
        });

        setVisible(true);
    }

    private void initializeTable() {
        // Table column names and data
        String[] columns = {"CategoryID", "Category Type", "Delete"};
        boolean isconnected=icc.startChecking();
        if(isconnected) {
            Object[][] data = cc.redirectAllCategoryData();

            // Create the table
            table = new JTable(new DefaultTableModel(data, columns));
            table.setFillsViewportHeight(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        else{
            Object[][] data=returntabledata();
            table = new JTable(new DefaultTableModel(data, columns));
            table.setFillsViewportHeight(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        // Add the custom button renderer and editor to the 'Delete' column
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Scroll pane for the table
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 800, 400);
        add(scrollPane);
    }

    private void refreshTableData() {
        // Fetch updated data from the database
        Object[][] newData = cc.redirectAllCategoryData();

        // Update the table model with the new data
        DefaultTableModel newModel = new DefaultTableModel(newData, new String[]{"CategoryID", "Category Type", "Delete"});
        table.setModel(newModel);

        // Reapply button renderers and editors to the "Delete" column
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Force table repaint
        table.repaint();
    }

    // Button Renderer for the 'Delete' button
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(Color.RED);
            setForeground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Button Editor for the 'Delete' button
    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private boolean isPushed;
        private JButton button;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);

            button.addActionListener(e -> {
                fireEditingStopped(); // Stop editing before action
                if (label.equals("Delete")) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Do you want to delete this category?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Get the ID of the category and call delete method
                        int categoryID = (int) table.getValueAt(row, 0);
                        boolean isconnected=icc.startChecking();
                        if(isconnected) {
                            cc.redirectCategoryDeleteRequest(categoryID);

                            // Refresh table data
                            refreshTableData();
                        }
                        {
                            storecategorytodelete(categoryID);
                        }

                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            label = "Delete"; // Label the button as "Delete"
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
    public void storecategorytodelete(int id){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("deletecategory.txt",true));
            bw.write(id);
            bw.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public Object[][] returntabledata() {
        BufferedReader br = null;
        Object[][] data = null;
        try {
            br = new BufferedReader(new FileReader("category.txt"));

            // Read all lines into a list
            LinkedList<String[]> lines = new LinkedList<>();
            String line;

            while ((line = br.readLine()) != null) {
                // Split each line by comma and add to the list
                String[] parts = line.split(",");
                if (parts.length == 2) { // Ensure line contains exactly two parts
                    lines.add(parts);
                }
            }

            // Convert the LinkedList to an Object[][]
            data = new Object[lines.size()][3];
            for (int i = 0; i < lines.size(); i++) {
                data[i][0] = lines.get(i)[0]; // ID
                data[i][1] = lines.get(i)[1]; // Type
                data[i][2]="Delete";
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }



}
