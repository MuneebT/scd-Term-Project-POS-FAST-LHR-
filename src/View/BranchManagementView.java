package View;

import Connection.InternetConnectionChecker;
import Controller.BranchManagementController;
import Model.BranchManagementModel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class BranchManagementView extends JFrame  {
    public JTable bmtable;
    private DefaultTableModel dtm1;
    private BranchManagementController bmc=new BranchManagementController();
    private JScrollPane bmScroll;
private InternetConnectionChecker icc=new InternetConnectionChecker();

public BranchManagementView(){
    setTitle("Branch Management");
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(null);
    setBounds(100, 100, 800, 600);

    // Table column names
    String[] columnNames = { "ID", "Name", "City", "Status", "Address", "PhoneNo", "No of Employees", "Delete", "Update" };

    Object[][] data1= bmc.return_object_Array();

    // Create the table with data and column names
    bmtable = new JTable(new DefaultTableModel(data1, columnNames));

    // Set custom renderer and editor for the last two columns (buttons)
    bmtable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
    bmtable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete"));

    bmtable.getColumn("Update").setCellRenderer(new ButtonRenderer());
    bmtable.getColumn("Update").setCellEditor(new ButtonEditor(new JCheckBox(), "Update"));

    // Scroll pane
    bmScroll = new JScrollPane(bmtable);
    bmScroll.setBounds(0, 50, getWidth(), getHeight());

    add(bmScroll);

    setVisible(true);
}

    // Custom renderer for rendering buttons in the table
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom editor for handling button actions in the table
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private String actionType;
        private int currentRow; // Store the current row

        public ButtonEditor(JCheckBox checkBox, String actionType) {
            super(checkBox);
            this.actionType = actionType;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            currentRow = row; // Capture the current row here
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {

                int code=0;


                if ("Delete".equals(actionType)) {
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to delete this data?");
                    if (response == 0) {
                        boolean isconnected = icc.startChecking();
                     if(isconnected){
                        DefaultTableModel model = (DefaultTableModel) bmtable.getModel();
                         code = (int) model.getValueAt(currentRow, 0);
                        System.out.println("Row to delete=" + currentRow);
                        System.out.println("code=" + code);
                        model.removeRow(currentRow);
                        bmc.redirect_delect_request(code);
                        JOptionPane.showMessageDialog(null, "Data Deleted from DB");
                    }
                     else{
                         storeBranchdatatodelete(code);
                     }
                    } else {
                        System.out.println("Data not deleted");
                    }
                } else if ("Update".equals(actionType)) {
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to update the data?");
                    if (response == 0) {
                        dispose();
                        // Open Update Screen (or dialog)
                        new UpdateScreenView(get_branch_id(), get_Branch_name(), get_branch_city(), get_branch_status(), get_branch_address(), get_branch_phoneno(), get_branch_employee_count());
                    } else {
                        System.out.println("Data not updated");
                    }
                }


            }
            isPushed = false;
            return label;
        }
    }


    //getter to get selected row id
  public  int get_branch_id(){
      Object num=bmtable.getValueAt(bmtable.getSelectedRow(),0);
        return num.hashCode();
    }

    //getter to get branch name
  public  String get_Branch_name(){
        Object name=bmtable.getValueAt(bmtable.getSelectedRow(),1);
        return name.toString();
    }

    //getter to get branch city
   public String get_branch_city(){
    Object city=bmtable.getValueAt(bmtable.getSelectedRow(),2);
    return city.toString();
    }

    //getter to get branch status
   public String get_branch_status(){
Object status= bmtable.getValueAt(bmtable.getSelectedRow(),3);
return  status.toString();
    }

    //getter for branch address
public    String get_branch_address(){
 Object address=bmtable.getValueAt(bmtable.getSelectedRow(),4);
    return address.toString();
    }

    // Getter for branch phone number
    public String get_branch_phoneno() {
     Object phoneno=bmtable.getValueAt(bmtable.getSelectedRow(),5);
     return phoneno.toString();
    }
    public int get_branch_employee_count(){
    Object employeecount=bmtable.getValueAt(bmtable.getSelectedRow(),6);
    return employeecount.hashCode();
    }
    public void storeBranchdatatodelete(int code){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("deleteBranchdata.txt",true));
            bw.write(code);
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
}
