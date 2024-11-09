package Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class UpdateScreenModel {

  public  LinkedList<String> read_city_name_from_file(){
    ObjectMapper mapper = new ObjectMapper();
    LinkedList<String> cityname=new LinkedList<>();
    try {
      // Read JSON file as an array of JSON nodes
      JsonNode citiesArray = mapper.readTree(new File("cities.json"));

      // Iterate through the array and print each city name
      for (JsonNode cityNode : citiesArray) {
        //System.out.println(cityNode.get("name").asText());
        cityname.add(cityNode.get("name").asText());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  return cityname;
  }
}
