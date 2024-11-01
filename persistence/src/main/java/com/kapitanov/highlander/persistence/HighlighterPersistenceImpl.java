package com.kapitanov.highlander.persistence;

import com.kapitanov.highlander.core.HighlightPersistence;
import com.kapitanov.highlander.core.domain.Highlight;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HighlighterPersistenceImpl implements HighlightPersistence {

    @Override
    public void save(Highlight highlight) {

    }

    @Override
    public List<Highlight> findAllByTitle(String title) {
        return List.of();
    }
}
