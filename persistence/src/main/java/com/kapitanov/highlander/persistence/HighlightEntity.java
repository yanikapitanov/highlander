package com.kapitanov.highlander.persistence;

import com.kapitanov.highlander.core.domain.Highlight;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("highlight")
public record HighlightEntity(
        @Id
        Long id,
        long hash,
        String author,
        String title,
        String quote,
        OffsetDateTime createdAt
) {

    static HighlightEntity from(Highlight highlight) {
        return new HighlightEntity(null,
                highlight.hashCode(),
                highlight.author(),
                highlight.title(),
                highlight.content(),
                OffsetDateTime.now());
    }
}
