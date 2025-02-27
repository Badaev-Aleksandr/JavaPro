package de.ait.homeworks.javaStreamAPI;

import java.util.Arrays;
import java.util.List;

public class TVProgramTestData {
    public static List<TVProgram> getTVProgramList() {
        return Arrays.asList(
                new TVProgram("Channel One", "Morning News", 30, true, 7.8),
                new TVProgram("Channel One", "Late Show", 45, false, 8.1),
                new TVProgram("SportsTV", "Football Match", 120, true, 9.0),
                new TVProgram("MovieMax", "Action Movie", 110, false, 8.5),
                new TVProgram("MovieMax", "Romantic Comedy", 100, false, 6.9),
                new TVProgram("EduChannel", "Science Doc", 60, false, 7.5),
                new TVProgram("ComedyFun", "Stand-up Special", 90, false, 8.2),
                new TVProgram("ComedyFun", "Improv Show", 25, true, 7.3)
        );
    }
}
