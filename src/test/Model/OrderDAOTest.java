package test.Model;
import Controller.OrderController;
import Model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDAOTest {
    private OrderController oc=new OrderController();

    @Test
    public void TestgetAllordersdoesnotreturnnull(){
        LinkedList<Order> orders=oc.returnALlOrders();
        Assertions.assertNotNull(orders,"Orders list is not null");
    }
    @Test
    public void TestgetAllOrdersisnotempty(){
        LinkedList<Order> orders=oc.returnALlOrders();
        Assertions.assertFalse(orders.isEmpty(),"Orders list is not empty");
    }
    @Test
    public void Testgetallorders(){
        LinkedList<Order> orders=oc.returnALlOrders();
        Order firstorder=orders.get(0);

        Assertions.assertEquals(1,firstorder.getProductID());
        Assertions.assertEquals("shampoo",firstorder.getProductName());
        Assertions.assertEquals(12,firstorder.getProductQuantity());
        Assertions.assertEquals(1,firstorder.getVendorID());
        Assertions.assertEquals("ali",firstorder.getVendorName());
        Assertions.assertEquals(1,firstorder.getBranchID());
    }

}
