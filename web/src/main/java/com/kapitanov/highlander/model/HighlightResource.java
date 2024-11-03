package com.kapitanov.highlander.model;

import com.kapitanov.highlander.core.domain.Highlight;
import jakarta.validation.constraints.NotBlank;

public record HighlightResource(@NotBlank String author,
                                @NotBlank String title,
                                @NotBlank String content) {


    public Highlight toHighlight() {
        return Highlight.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();
    }

    public static HighlightResource from(Highlight highlight) {
        return new HighlightResource(highlight.author(), highlight.title(), highlight.content());
    }
}
