package test.Model;
import Controller.CategoryController;
import Model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
public class CategoryDAOTest {
    private CategoryController cc=new CategoryController();
    @Test
    public void TestgetAllcategoriesdoesnotreturnnull(){
        String expected[]={"Electronics","Groceries","Stationary","Toys"};
        LinkedList<Category> categories=cc.redirectgetAllCategoriesRequest();
        String type[]=new String[categories.size()];
        for(int i=0;i< categories.size();i++){
            type[i]=categories.get(i).gettype();
        }
        Assertions.assertArrayEquals(expected,type);

    }
}
