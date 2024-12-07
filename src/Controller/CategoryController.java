package Controller;

import Model.Category;
import Model.CategoryDAO;

import java.util.LinkedList;

public class CategoryController {

    public  void redirectinsertRequest(String type){
        CategoryDAO.insertTypeintoCategoryTable(type);
    }
    public LinkedList<Category> redirectgetAllCategoriesRequest(){
        return CategoryDAO.getAllCategories();
    }
}
