import person.Education;
import person.Person;
import person.Sex;
import java.util.*;
import java.util.stream.Collectors;
import static person.Education.HIGHER;
import static person.Sex.MAN;
import static person.Sex.WOMAN;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        //Task1
        System.out.println("Количество несовершеннолетних: " + searchMinors(persons));

        //Task2
        System.out.println("\nСписок фамилий призывников: ");
        conscriptList(persons).forEach(System.out::println);

        //Task3
        System.out.println("\nСписок потенциально работоспособных людей с высшим образованием в выборке: ");
        sortedPeopleWithHigherEducation(persons).forEach(System.out::println);
    }

    public static Long searchMinors(Collection<Person> personsCollection) {
        return personsCollection.stream()
                .map(Person::getAge)
                .filter(age -> age < 18)
                .count();
    }

    public static List<String> conscriptList(Collection<Person> personsCollection) {
        return personsCollection.stream()
                .filter(person -> person.getSex().equals(MAN))
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
    }

    public static Collection<String> sortedPeopleWithHigherEducation(Collection<Person> personsCollection) {
        return personsCollection.stream()
                .filter(person -> person.getEducation().equals(HIGHER))
                .filter(person -> person.getSex().equals(WOMAN)
                        && person.getAge() >= 18
                        && person.getAge() <= 60
                        || person.getSex().equals(MAN)
                        && person.getAge() >= 18
                        && person.getAge() <= 65)
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::getFamily)
                .collect(Collectors.toList());
    }
}
