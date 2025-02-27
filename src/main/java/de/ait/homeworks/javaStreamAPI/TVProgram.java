package de.ait.homeworks.javaStreamAPI;

public class TVProgram {
    private String channel;      // Название канала
    private String programName;  // Название передачи
    private int duration;        // Длительность передачи (в минутах)
    private boolean isLive;      // Признак прямого эфира
    private double rating;       // Рейтинг передачи (например, от 0.0 до 10.0)

    public TVProgram(String channel, String programName, int duration, boolean isLive, double rating) {
        this.channel = channel;
        this.programName = programName;
        this.duration = duration;
        this.isLive = isLive;
        this.rating = rating;
    }

    public String getChannel() {
        return channel;
    }

    public String getProgramName() {
        return programName;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isLive() {
        return isLive;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return String.format(
                "TVProgram{channel='%s', programName='%s', duration=%d, isLive=%b, rating=%.1f}",
                channel, programName, duration, isLive, rating
        );
    }
}
