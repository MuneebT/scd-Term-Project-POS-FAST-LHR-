package Controller;

import Model.Category;
import Model.CategoryDAO;

import java.util.LinkedList;

public class CategoryController {
    private Category obj=new Category();

    public  void redirectinsertRequest(String type){
        CategoryDAO.insertTypeintoCategoryTable(type);
    }
    public LinkedList<Category> redirectgetAllCategoriesRequest(){
        return CategoryDAO.getAllCategories();
    }

    public Object[][] redirectAllCategoryData(){
        return obj.objectarrayofCategoryData();
    }
    public void redirectCategoryDeleteRequest(int id){
        CategoryDAO.deleteCategory(id);
    }
}
