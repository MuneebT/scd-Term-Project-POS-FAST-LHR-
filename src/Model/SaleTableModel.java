package Model;

import View.SaleTableView;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SaleTableModel extends AbstractTableModel {
    private List<Sale> salesList;
    private String[] columnNames = {
            "Sale ID",
            "Product ID",
            "Product Name",
            "Price",
            "Quantity",
            "Total Price",
            "Invoice Number",
            "Branch ID"
    };

    public SaleTableModel(List<Sale> salesList) {
        this.salesList = salesList;
    }

    @Override
    public int getRowCount() {
        return salesList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sale sale = salesList.get(rowIndex);
        switch (columnIndex) {
            case 0: return sale.getSaleId();
            case 1: return sale.getProdId();
            case 2: return sale.getProdName();
            case 3: return sale.getPrice();
            case 4: return sale.getQuantity();
            case 5: return sale.getTotalPrice();
            case 6: return sale.getInvoiceNumber();
            case 7: return sale.getBranchID(); // Added branchID handling
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Define which cells can be edited if needed
        return false; // No cells are editable by default
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Sale sale = salesList.get(rowIndex);
        switch (columnIndex) {
            case 0: sale.setSaleId(Integer.parseInt((String) aValue)); break;
            case 1: sale.setProdId(Integer.parseInt((String) aValue)); break;
            case 2: sale.setProdName((String) aValue); break;
            case 3: sale.setPrice((Double) aValue); break;
            case 4: sale.setQuantity((Integer) aValue); break;
            case 5: sale.setTotalPrice((Double) aValue); break;
            case 6: sale.setInvoiceNumber(Integer.parseInt((String) aValue)); break;
            case 7: sale.setBranchID(Integer.parseInt((String) aValue)); break; // Added branchID handling
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public static void main(String[] args) {
        new SaleTableView("1");
    }
}
