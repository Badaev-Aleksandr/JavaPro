package de.ait.homeworks.javaStreamAPI;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TVProgramApp {
    private static List<TVProgram> result;

    public static void main(String[] args) {
        List<TVProgram> tvProgramList = TVProgramTestData.getTVProgramList();
//        result = findProgramsAboveRating(tvProgramList, 8);
//        for (TVProgram program : result) {
//            System.out.println(program);
//        }

        transferToStringFormat(tvProgramList);


    }

    public static List<TVProgram> findProgramsAboveRating(List<TVProgram> tvProgramList, double rating) {
        return tvProgramList.stream()
                .filter(tvProgram -> tvProgram.getRating() > rating)
                .collect(Collectors.toList());
    }

    public static void transferToStringFormat(List<TVProgram> tvProgramList) {
        tvProgramList.stream()
                .map(tvProgram -> String.format("Канал: %s | Передача: %s | Рейтинг: %.1f", tvProgram.getChannel(),tvProgram.getProgramName(),tvProgram.getRating()))
                .forEach(System.out::println);
    }

    public static boolean isLive(List<TVProgram> tvPrograms) {
        return tvPrograms.stream().anyMatch(tvProgram -> tvProgram.isLive());
    }

    public static TVProgram findLongestProgram(List<TVProgram> tvProgramList) {
        Optional<TVProgram> longestTypProgram = tvProgramList.stream()
                .max(Comparator.comparingInt(TVProgram::getDuration));
            return longestTypProgram.orElse(null);
    }
}
