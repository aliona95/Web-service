/**
 * Created by Aliona and Eimantas
 */

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Data {
    private Map<Integer, Person> people = new HashMap<>();
    private Field[] fields;

    Data(){
        List<Person> peopleArray = Arrays.asList(
            new Person(1, "Petras", "Petraitis", "male", "Ikalnes 45", new int[]{1,2,3}),
            new Person(2, "Juozas", "Juozaitis", "male", "Geliu 5", new int[]{1,6}),
            new Person(3, "Maryte", "Kuodyte", "female", "Pievu 32", new int[]{6}),
            new Person(4, "Stasele", "Stasyte", "female", "Klevu 71", new int[]{5}),
            new Person(5, "Stasys", "Jonaitis", "male", "Siltnamiu 6", new int[]{5,2,3})
        );
        peopleArray.forEach(person -> {this.people.put(person.getId(), person);
        });
    }

    public Person get(int id){
        return people.get(id);
    }
    public List<Person> getAll(){
        return people.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
    /*
    public Person getCompany(int id){
        return people.get(id);
    }
    */
    public List<Person> getByCompany(int id){
        List<Person> peopleList = new ArrayList<Person> ();
        for(Map.Entry<Integer, Person> entry:people.entrySet()){
            int [] compId = entry.getValue().getCompanyId();
            for(int i = 0; i < compId.length; i++){
                if(compId[i] == id){
                    peopleList.add(entry.getValue());
                }
            }
        }
        return peopleList;
    }
    public int findMaxId(){
        int maxId = 0;
        for(Map.Entry<Integer, Person> entry:people.entrySet()){
            if(entry.getValue().getId() > maxId){
                maxId = entry.getValue().getId() ;
            }
        }
        return maxId + 1;
    }
    public void addPerson(Person person){
        person.setId(findMaxId());
        people.put(person.getId(), person);
    }
    public void removePerson(int id){
        people.remove(id);
    }

    public void update(Integer id, Person person) {
        person.setId(id);
        people.put(id, person);
    }
    public List<Person> findByName(String name){
        return people.entrySet().stream().filter(
                (entry) -> entry.getValue().getName().equals(name)
        ).map(Map.Entry::getValue).collect(Collectors.toList());
    }
    public List<Person> findByGender(String gender){
        return people.entrySet().stream().filter(
                (entry) -> entry.getValue().getGender().equals(gender)
        ).map(Map.Entry::getValue).collect(Collectors.toList());
    }
    public boolean isPersonValid(Person person) {
        if (person.getName() == null || person.getSurname() == null || person.getGender() == null || person.getAddress() == null || person.getCompanyId() == null){
            return false;
        }
        if (!person.getGender().equals("male") && !person.getGender().equals("female")){
            return false;
        }
        return true;
    }
    public String personMissedFields(Person person){
        if(person.getName() == null){
            return "vardo";
        }
        if(person.getSurname()  == null){
            return "pavardes";
        }
        if(person.getGender()  == null || (!person.getGender().equals("male") && !person.getGender().equals("female"))){
            return "lyties";
        }
        if(person.getAddress() == null){
            return "adreso";
        }
        return null;
    }
    public boolean checkIdExists(int id){
        for(Map.Entry<Integer, Person> entry:people.entrySet()){
            if(entry.getValue().getId() == id){
                return true;
            }
        }
        return false;
    }
}
