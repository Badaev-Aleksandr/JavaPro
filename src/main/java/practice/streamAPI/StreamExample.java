package practice.streamAPI;

import java.util.List;
import java.util.stream.Collectors;

public class StreamExample {
    public static void main(String[] args) {
        //Преобразовать список строк в список их длин:
        List<String> words = List.of("apple", "banana", "cherry");

        List<Integer> wordsLength = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(wordsLength);

        //Извлечь имена из списка пользователей:
        List<User> users = List.of(
                new User("Alice", 30),
                new User("Bob", 25),
                new User("Charlie", 28)
        );

        List<String> usersName = users.stream()
                .map(User::getName)
                .collect(Collectors.toList());
        System.out.println(usersName);

        //Преобразовать список чисел в строки:
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        List<String> numbersString = numbers.stream()
                .map(num -> String.valueOf(num))
                .collect(Collectors.toList());
        System.out.println(numbersString);

        //Добавить к каждому имени префикс "User: ":
        List<String> names = List.of("Anna", "John", "Peter");

        List<String> namesPlusPrefix = names.stream()
                .map(name -> "User: " + name)
                .collect(Collectors.toList());
        System.out.println(namesPlusPrefix);

        //Преобразовать список в верхний регистр:
        List<String> cities = List.of("Berlin", "Paris", "London");

        List<String> citiesUpperCase = cities.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(citiesUpperCase);

        //Извлечь фамилии из полного имени:
        List<String> fullNames = List.of("John Smith", "Anna Johnson", "Peter Brown");

        List<String> lastNames = fullNames.stream()
                .map(name -> name.split(" ")[1])
                .collect(Collectors.toList());
        System.out.println(lastNames);

        //Преобразовать список температур из Цельсия в Фаренгейт:
        //Формула: F = C × 9/5 + 32
        List<Double> celsius = List.of(0.0, 20.0, 37.0, 100.0);

        List<Double> fahrenheit = celsius.stream()
                .map(F -> F * 9 / 5 + 32)
                .collect(Collectors.toList());
        System.out.println(fahrenheit);

        //Извлечь доменные имена из email-адресов:
        List<String> emails = List.of("user1@gmail.com", "admin@yahoo.com", "test@hotmail.com");

        List<String> domeEmails = emails.stream()
                .map(email -> email.split("@")[1])
                .collect(Collectors.toList());
        System.out.println(domeEmails);

        //Преобразовать список строк в их хэши (хэш-коды):
        List<String> data = List.of("alpha", "beta", "gamma");

        data.stream()
                .map(String::hashCode)
                .forEach(System.out::println);

        //Конкатенировать имя и возраст из списка объектов:

        List<String> people = users.stream()
                .map(person -> String.valueOf(person.getName() + " " + "(" + person.getAge() + ")"))
                .collect(Collectors.toList());
        System.out.println(people);
    }
}
