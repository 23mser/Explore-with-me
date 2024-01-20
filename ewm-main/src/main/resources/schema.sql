DROP TABLE IF EXISTS requests, event_compilations, events, compilations, categories, users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email varchar(254) NOT NULL UNIQUE,
    name  varchar(250) NOT NULL
    );

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS locations
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation         varchar(2000),
    category_id        BIGINT REFERENCES categories (id),
    confirmed_requests INT,
    created_on         TIMESTAMP     NOT NULL,
    description        VARCHAR(7000) NOT NULL,
    event_date         TIMESTAMP     NOT NULL,
    initiator_id       BIGINT        REFERENCES users (id) ON DELETE SET NULL,
    location_id        BIGINT        REFERENCES locations (id) ON DELETE SET NULL,
    paid               BOOLEAN       NOT NULL,
    participant_limit  INTEGER DEFAULT 0,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN DEFAULT TRUE,
    state              VARCHAR(10),
    title              VARCHAR(120)  NOT NULL,
    views              INTEGER DEFAULT 0
    );

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created      TIMESTAMP NOT NULL,
    event_id     BIGINT REFERENCES events (id) ON DELETE CASCADE,
    requester_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
    status       VARCHAR(10)
    );

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title  VARCHAR(50) NOT NULL,
    pinned BOOLEAN     NOT NULL
    );

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilation_id BIGINT NOT NULL,
    event_id       BIGINT NOT NULL,
    CONSTRAINT compilation_event_pk PRIMARY KEY(compilation_id, event_id),
    FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
    );