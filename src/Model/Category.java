package Model;

public class Category {
    int id;
    String type;
    public Category(int id,String type){
        this.id=id;
        this.type=type;
    }
  public  String gettype(){
        return type;
    }
}
