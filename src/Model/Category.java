package Model;

import java.util.LinkedList;

public class Category {
    int id;
    String type;

    public Category(){

    }
    public Category(int id,String type){
        this.id=id;
        this.type=type;
    }
  public  String gettype(){
        return type;
    }

    public Object[][] objectarrayofCategoryData(){
        LinkedList<Category> categories=CategoryDAO.getAllCategories();
        Object[][] data=new Object[categories.size()][3];
        for(int i=0;i<categories.size();i++){
            data[i][0]=categories.get(i).id;
            data[i][1]=categories.get(i).type;
            data[i][2]="Delete";
        }
        return data;
    }
}
