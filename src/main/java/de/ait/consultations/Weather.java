package de.ait.consultations;

public class Weather {
    private String city;
    private double temperature;
    private boolean isRainy;

    public Weather(String city, double temperature, boolean isRainy) {
        this.city = city;
        this.temperature = temperature;
        this.isRainy = isRainy;
    }

    public String getCity() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean isRainy() {
        return isRainy;
    }

    @Override
    public String toString() {
        return "Weather{" + "city='" + city + '\'' + ", temperature=" + temperature + ", isRainy=" + isRainy + '}';
    }
}
