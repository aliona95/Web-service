/**
 * Created by Aliona and Eimantas
 */
public class Person {
    private int id = 0;
    private String name;
    private String surname;
    private String gender;
    private String address;

    Person(int id, String name, String surname, String gender, String address){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.address = address;
    }

    // setters
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setAddress(String address){
        this.address = address;
    }

    // getters
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getGender(){
        return gender;
    }
    public String getAddress(){
        return address;
    }
}
