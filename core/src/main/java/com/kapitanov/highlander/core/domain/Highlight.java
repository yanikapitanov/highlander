package com.kapitanov.highlander.core.domain;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static java.util.function.Predicate.not;

public record Highlight(String author, String title, String content) {

    private static final BiFunction<Integer, String, String> FIND_AUTHOR = (index, line) -> line.substring(index + 1)
            .trim()
            .replaceAll("\\)", "");
    private static final BiFunction<Integer, String, String> FIND_TITLE = (i, line) -> line.substring(i + 1);

    public static Highlight from(String highlightAsText) {
        List<String> lines = Arrays.stream(highlightAsText.split("\n"))
                .filter(not(String::isBlank))
                .toList();
        String titleAndAuthor = lines.getFirst();
        String quote = lines.getLast();
        int split = titleAndAuthor.lastIndexOf("(");

        String author = findContent(FIND_AUTHOR, split, titleAndAuthor);
        String title = findContent(FIND_TITLE, split, titleAndAuthor);

        return Highlight.builder()
                .author(author)
                .title(title)
                .content(quote)
                .build();
    }

    private static String findContent(BiFunction<Integer, String, String> content, int index, String line) {
        return content.apply(index, line);
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
