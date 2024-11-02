package com.kapitanov.highlander.core.domain;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static java.util.function.Predicate.not;

public record Highlight(String author, String title, String content) {

    public static Highlight from(String highlightAsText) {
        List<String> lines = Arrays.stream(highlightAsText.split("\n"))
                .filter(not(String::isBlank))
                .toList();
        String titleAndAuthor = lines.getFirst();
        String quote = lines.getLast();
        int split = titleAndAuthor.lastIndexOf("(");

        String auth = findContent(() -> titleAndAuthor.substring(split + 1)
                .trim()
                .replaceAll("\\)", ""));
        String title = findContent(() -> titleAndAuthor.substring(0, split).trim());

        return Highlight.builder()
                .author(auth)
                .title(title)
                .content(quote)
                .build();
    }

    private static String findContent(Supplier<String> findContent) {
        return findContent.get();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String author;
        private String title;
        private String content;

        private Builder() {

        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Highlight build() {
            return new Highlight(author, title, content);
        }
    }
}
