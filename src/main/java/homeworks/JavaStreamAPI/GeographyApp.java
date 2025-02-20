package homeworks.JavaStreamAPI;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GeographyApp {
    static private List<String> countries = Arrays.asList("Germany", "France", "Brazil", "Argentina", "Canada",
            "China", "Australia", "India");

    static private List<String> cities = Arrays.asList("Berlin", "Buenos Aires", "Paris",
            "Los Angeles", "New York", "London", "Beijing");

    static private List<String> rivers = Arrays.asList("Amazon", "Nile", "Yangtze", "Mississippi", "Danube",
            "Main", "Ganges");

    static private List<String> continents = Arrays.asList("Europe", "Asia", "Africa", "Australia", "Antarctica",
            "South America", "North America");

    static private List<String> countriesList = Arrays.asList("Mexico", "Sweden", "Brazil", "USA", "Canada",
            "France", "Norway");


    public static void main(String[] args) {

        //1. Фильтрация стран по первой букве
        System.out.println("1. Фильтрация стран по заданной первой букве");
        for(String country: getCountriesByPrefix(countries, "C")){
            System.out.print(country + ", ");
        }

        //2. Фильтрация городов по длине названия
        System.out.println("\n2. Фильтрация городов по заданной длине названия");
        for(String city: filterCitiesByLength(cities, 6)){
            System.out.print(city + ", ");
        }

        //3. Фильтрация рек с четным количеством букв в названии
        System.out.println("\n3. Фильтрация рек с четным количеством букв в названии");
        for (String river: filterRiversByEvenLength(rivers)){
            System.out.print(river + ", ");
        }

        //4. Фильтрация континентов по количеству символов
        System.out.println("\n4. Фильтрация континентов по заданному количеству символов");
        for (String continent: filterContinentsByLength(continents, 7)){
            System.out.print(continent + ", ");
        }

        //5. Фильтрация стран с названием из количества букв
        System.out.println("\n5. Фильтрация стран с названием из заданного количества букв");
        for (String country: filterCountriesByLength(countriesList, 6)){
            System.out.print(country + ", ");
        }

        //6. Поиск стран с названием, содержащим букву
        System.out.println("\n6. Поиск стран с названием, содержащим заданную букву");
        for (String country: searchCountriesWithPrefix(countriesList, "a")){
            System.out.print(country + ", ");
        }

        //7. Фильтрация городов по последней букве
        System.out.println("\n7. Фильтрация городов по последней заданной букве");
        for(String city: filterCitiesByLastPrefix(cities, "s")){
            System.out.print(city + ", ");
        }

        //8. Определение рек, содержащих более заданных букв
        System.out.println("\n8. Определение рек, содержащих более заданных букв");
        for (String river: filterRiversByLength(rivers, 7)){
            System.out.print(river + ", ");
        }

        //9. Фильтрация континентов по заданной первой букве
        System.out.println("\n9. Фильтрация континентов по заданной первой букве");
        for (String continent: filterContinentsByFirstPrefix(continents, "A")){
            System.out.print(continent + ", ");
        }

    }
    static private List<String> getCountriesByPrefix(List<String> countries, String prefix) {
        return countries.stream()
                .filter(country -> country.toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }

    static private List<String> filterCitiesByLength(List<String> cities, int length) {
    return cities.stream()
            .filter(city -> city.length() > length)
            .collect(Collectors.toList());
    }

    static private List<String> filterRiversByEvenLength(List<String> rivers) {
        return rivers.stream()
                .filter(river -> river.length() % 2 == 0)
                .collect(Collectors.toList());
    }

    static private List<String> filterContinentsByLength(List<String> continents, int length) {
        return continents.stream()
                .filter(continent -> continent.length() < length)
                .collect(Collectors.toList());
    }

    static private List<String> filterCountriesByLength(List<String> continents, int length) {
        return continents.stream()
                .filter(country -> country.length() == length)
                .collect(Collectors.toList());
    }

    static private List<String> searchCountriesWithPrefix(List<String> countries, String prefix) {
        return countries.stream()
                .filter(country -> country.toLowerCase().contains(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }

    static private List<String> filterCitiesByLastPrefix(List<String> cities, String prefix) {
        return cities.stream()
                .filter(city -> city.toLowerCase().endsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }

    static private List<String> filterRiversByLength(List<String> rivers, int length) {
        return rivers.stream()
                .filter(river -> river.length() > length)
                .collect(Collectors.toList());
    }

    static private List<String> filterContinentsByFirstPrefix(List<String> continents, String prefix) {
       return continents.stream()
               .filter(continent -> continent.toLowerCase().startsWith(prefix.toLowerCase()))
               .collect(Collectors.toList());
    }
}

