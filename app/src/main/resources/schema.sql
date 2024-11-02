CREATE TABLE IF NOT EXISTS HIGHLIGHT (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    hash BIGINT NOT NULL,
    author VARCHAR NOT NULL,
    title VARCHAR NOT NULL,
    content VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX if not exists highlight_hash on highlight(hash);
