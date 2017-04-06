/**
 * Created by Aliona and Eimantas
 */

import java.util.*;
import java.util.stream.Collectors;

public class Data {
    private Map<Integer, Person> people = new HashMap<>();

    Data(){
        List<Person> peopleArray = Arrays.asList(
            new Person(1, "Petras", "Petraitis", "male", "Ikalnes 45"),
            new Person(2, "Juozas", "Juozaitis", "male", "Geliu 5"),
            new Person(3, "Maryte", "Kuodyte", "female", "Pievu 32"),
            new Person(4, "Stasele", "Stasyte", "female", "Klevu 71"),
            new Person(5, "Stasys", "Jonaitis", "male", "Siltnamiu 6")
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
    public void addPerson(Person person){
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
}
