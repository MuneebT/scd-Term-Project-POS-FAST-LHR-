package test.Model;
import Controller.InventoryCntroller;
import Model.Inventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class InventoryDAOTest {
    private InventoryCntroller ic=new InventoryCntroller();
    @Test
    public void TestgetAllInventroydoesnotreturnnull(){
       LinkedList<Inventory> inventory=ic.returnAllInventory();
        Assertions.assertNotNull(inventory,"Inventory ist i not null");
    }
    @Test
    public void TestgetAllInventorylistisnotempty(){
        LinkedList<Inventory> inventory=ic.returnAllInventory();
        Assertions.assertFalse(inventory.isEmpty(),"Inventory list is not empty");
    }
    @Test
    public void TestgetAllemployees(){
        LinkedList<Inventory> inventory=ic.returnAllInventory();
        Inventory firstinventory=inventory.get(0);
        Assertions.assertEquals(1,firstinventory.getProductId());
        Assertions.assertEquals("Shampoo",firstinventory.getProductName());
        Assertions.assertEquals(11,firstinventory.getProductQuantity());
        Assertions.assertEquals("Groceries",firstinventory.getProductCategory());
        Assertions.assertEquals(120,firstinventory.getCostPrice());
        Assertions.assertEquals(130,firstinventory.getSalePrice());
    }
}
