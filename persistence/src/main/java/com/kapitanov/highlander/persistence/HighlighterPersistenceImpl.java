package com.kapitanov.highlander.persistence;

import com.kapitanov.highlander.core.HighlightPersistence;
import com.kapitanov.highlander.core.domain.Highlight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
class HighlighterPersistenceImpl implements HighlightPersistence {

    private static final Logger LOGGER = LoggerFactory.getLogger(HighlighterPersistenceImpl.class);
    private final JdbcClient jdbcClient;

    HighlighterPersistenceImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    @Override
    public void save(Highlight highlight) {
        LOGGER.info("Saving highlight {}", highlight);
        String sql = """
                INSERT INTO highlight (hash, author, title, content, created_at)
                VALUES (?, ?, ?, ?, ?)
                """;

        Optional<Highlight> byHash = findByHash(highlight.hashCode());

        if (byHash.isPresent()) {
            LOGGER.warn("{} already saved", highlight);
            return;
        }

        jdbcClient.sql(sql)
                .param(1, highlight.hashCode())
                .param(2, highlight.author())
                .param(3, highlight.title())
                .param(4, highlight.content())
                .param(5, OffsetDateTime.now())
                .update();
    }

    @Override
    public void saveAll(Collection<Highlight> highlights) {
        highlights.forEach(this::save);
    }

    @Override
    public List<Highlight> findAll() {
        String sql = """
                select hash, author, title, content, created_at from highlight
                """;
        return jdbcClient.sql(sql)
                .query(Highlight.class)
                .list();
    }

    @Override
    public long findNumberOfEntries() {
        String sql = """
                select count(*) from highlight
                """;
        return jdbcClient.sql(sql).query().listOfRows().size();
    }

    private Optional<Highlight> findByHash(int hash) {
        String statement = """
                select hash, author, title, content, created_at from highlight where hash = ?
                """;
        return jdbcClient.sql(statement)
                .param(1, hash)
                .query(Highlight.class)
                .optional();
    }
}
