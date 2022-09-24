import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        //Поиск количества несовершеннолетних (т.е. людей младше 18 лет).
        Stream<Person> stream1 = persons.stream();
        System.out.println(persons.stream().filter(x -> x.getAge() < 18).count());

        //Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет)
        Stream<Person> stream2 = persons.stream();
        System.out.println(persons.stream()
                .filter(x -> x.getAge() > 17)
                .filter(x -> x.getAge() < 28)
                .filter(x -> x.getSex() == Sex.MAN)
                .map(x -> x.getFamily() + x.getAge())
                .collect(Collectors.toList()));

        //Получить отсортированный по фамилии список потенциально работоспособных
        //людей с высшим образованием в выборке (т.е. людей с высшим образованием
        //от 18 до 60 лет для женщин и до 65 лет для мужчин).
        Stream<Person> stream3 = persons.stream();
        System.out.println(persons.stream()
                .filter(x -> x.getEducation() == Education.HIGHER)
                .filter(x -> {
                    if (x.getSex() == Sex.MAN) {
                        return (x.getAge() > 17 && x.getAge() < 66);
                    } else if (x.getSex() == Sex.WOMAN) {
                        return (x.getAge() > 17 && x.getAge() < 61);
                    }
                    return false;
                })
                .sorted(Comparator.comparing(Person::getFamily))
                .map(x -> x.getFamily() + " " + x.getAge() + " " + x.getSex() + " " + x.getEducation())
                .collect(Collectors.toList()));
    }

}