public class Company {
    private int id;
    private String name;
    private String city;
    private int phoneNumber;
    public Company(int id, String name, String city, int phoneNumber){
        this.id = id;
        this.name = name;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }
    Company(){
        
    }
    public String getName(){
        return this.name;
    }
    public String getCity(){
        return this.city;
    }
    public int getId(){
        return this.id;
    }
    public int phoneNumber(){
    	return this.phoneNumber;
    }
    public void setName(String name){
    	this.name = name;
    }
    public void setCity(String city){
    	this.city = city;
    }
    public void setPhoneNumber(int phoneNumber){
    	this.phoneNumber = phoneNumber;
    }
    public void setId(int id){
    	this.id = id; 
    }
} 
