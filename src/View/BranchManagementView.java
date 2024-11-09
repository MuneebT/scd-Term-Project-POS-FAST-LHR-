package View;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class BranchManagementView extends JFrame  {
    public JTable bmtable;
    private DefaultTableModel dtm1;
    private JScrollPane bmScroll;


public BranchManagementView(){
    setTitle("Branch Management");
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(null);
    setBounds(100, 100, 800, 600);

    // Table column names
    String[] columnNames = { "Code", "Name", "City", "Status", "Address", "PhoneNo", "No of Employees", "Delete", "Update" };

    // Sample data for the table
    Object[][] data = {
            { "B001", "Branch 1", "Gujranwala", "Active", "123 Street", "123-456-7890", 50, "Delete", "Update" },
            { "B002", "Branch 2", "Lahore", "Inactive", "456 Avenue", "098-765-4321", 40, "Delete", "Update" },
            { "B003", "Branch 3", "karachi", "Active", "789 Boulevard", "111-222-3333", 60, "Delete", "Update" }
    };

    // Create the table with data and column names
    bmtable = new JTable(new DefaultTableModel(data, columnNames));

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
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Handle Delete or Update action based on the action type
                if ("Delete".equals(actionType)) {
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to delete this data?");
                    if (response == 0) {
                        // Get the selected row index
                        int rowToDelete = bmtable.getSelectedRow();
                        if (rowToDelete != -1) {

                            DefaultTableModel model = (DefaultTableModel) bmtable.getModel();
                            model.removeRow(rowToDelete);
                        }
                    } else {
                        System.out.println("Data not deleted");
                    }
                } else if ("Update".equals(actionType)) {
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to update the data?");
                    if (response == 0) {
                        // Open Update Screen (or dialog)
                        new UpdateScreenView(get_Branch_name(),get_branch_city(),get_branch_status(),get_branch_address(),get_branch_phoneno());
                    } else {
                        System.out.println("Data not updated");
                    }
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

//        @Override
//        protected void fireEditingStopped() {
//            super.fireEditingStopped();
//        }
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

}
