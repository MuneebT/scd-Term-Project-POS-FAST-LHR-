package Controller;

import Model.UpdateScreenModel;

import java.util.LinkedList;

public class UpdateScreenController {
    private UpdateScreenModel usm1=new UpdateScreenModel();

  public  LinkedList<String> return_list_of_city_names(){
        return usm1.read_city_name_from_file();
    }

}
