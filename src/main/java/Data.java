import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Aliona and Eimantas
 */
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
}
