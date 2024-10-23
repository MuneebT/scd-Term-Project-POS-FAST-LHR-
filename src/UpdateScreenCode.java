public class UpdateScreenCode {

    boolean verify_Phone_No_length(String phone_no){

        if(phone_no.length()>11|| phone_no.length()<11){
        return false;
        }
        return true;
    }
    boolean verify_Phone_No_Data(String phone_no){
        String regex = "^[0-9]+$";
        return phone_no.matches(regex);
    }
}
