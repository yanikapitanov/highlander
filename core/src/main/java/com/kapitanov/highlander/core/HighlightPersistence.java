package com.kapitanov.highlander.core;

import com.kapitanov.highlander.core.domain.Highlight;
import org.springframework.stereotype.Component;

import java.util.List;

public interface HighlightPersistence {

    void save(Highlight highlight);

    List<Highlight> findAllByTitle(String title);
}
