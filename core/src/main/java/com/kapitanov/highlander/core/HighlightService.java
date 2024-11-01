package com.kapitanov.highlander.core;

import com.kapitanov.highlander.core.domain.Highlight;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


@Service
public class HighlightService {

    private final HighlightPersistence persistence;

    public HighlightService(HighlightPersistence persistence) {
        this.persistence = persistence;
    }

    public List<Highlight> parse(String highlights) {

        return Arrays.stream(highlights.split("=========="))
                .filter(Predicate.not(String::isEmpty))
                .map(Highlight::from)
                .toList();
    }

    public void save(Highlight highlight) {
        System.out.println(highlight);
        persistence.save(highlight);
    }
}
