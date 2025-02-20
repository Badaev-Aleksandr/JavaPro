package de.ait.javalessons.streamApi;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class YouTubeAnalyzerTest {
    private static List<YouTubeVideo> videos = List.of(
            new YouTubeVideo("Как научиться программировать", "IT Channel", 15000000, 12000, 720, "Образование", true),
            new YouTubeVideo("Лучшие моменты матча", "Sports Channel", 500000, 8000, 600, "Спорт", false),
            new YouTubeVideo("Новый трек 2025", "Music Channel", 3000000, 25000, 240, "Музыка", true),
            new YouTubeVideo("Обзор новой игры", "Gaming Channel", 2000000, 15000, 900, "Игры", true),
            new YouTubeVideo("Как приготовить пиццу", "Cooking Channel", 800000, 10000, 1200, "Кулинария", false),
            new YouTubeVideo("Как приготовить кофе", "Cooking Channel", 100_000, 70000, 1400, "Кулинария", true));


    @Test
    void testIsMore10MSuccess() {
        //Average
        YouTubeAnalyzer youTubeAnalyzer = new YouTubeAnalyzer();

        //Action
        boolean result = youTubeAnalyzer.isMore10M(videos);

        //Assert
        assertTrue(result);
    }
}
