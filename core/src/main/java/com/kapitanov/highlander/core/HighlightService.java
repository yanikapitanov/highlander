package com.kapitanov.highlander.core;

import com.kapitanov.highlander.core.domain.Highlight;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.IntStream;


@Service
public class HighlightService {

    private final HighlightPersistence persistence;

    public HighlightService(HighlightPersistence persistence) {
        this.persistence = persistence;
    }

    public void saveHighlightsFromFile(String highlightsFile) {
        Objects.requireNonNull(highlightsFile, "Files cannot be null");
        List<Highlight> parsedHighlights = parse(highlightsFile);

        persistence.saveAll(parsedHighlights);
    }

    private List<Highlight> parse(String highlights) {
        Objects.requireNonNull(highlights, "Files cannot be null");

        return Arrays.stream(highlights.split("=========="))
                .filter(Predicate.not(String::isEmpty))
                .map(Highlight::from)
                .toList();
    }

    public void save(Highlight highlight) {
        Objects.requireNonNull(highlight, "Highlight cannot be null");
        persistence.save(highlight);
    }

    public List<Highlight> findFiveRandomHighlights() {
        List<Highlight> all = persistence.findAll();
        return IntStream.range(0, 5)
                .boxed()
                .map(i -> all.get(ThreadLocalRandom.current().nextInt(all.size())))
                .limit(5)
                .toList();
    }
}
