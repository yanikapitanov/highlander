package com.kapitanov.highlander.core;

import com.kapitanov.highlander.core.domain.Highlight;

import java.util.Collection;
import java.util.List;

public interface HighlightPersistence {

    void save(Highlight highlight);

    void saveAll(Collection<Highlight> highlights);

    List<Highlight> findAll();

    long findNumberOfEntries();
}
