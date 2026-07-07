CREATE TABLE customers (
    id             UUID PRIMARY KEY,
    name           VARCHAR(200) NOT NULL,
    email          VARCHAR(200) NOT NULL,
    document       VARCHAR(11)  NOT NULL,
    status         VARCHAR(20)  NOT NULL,
    registered_at  TIMESTAMP    NOT NULL,
    CONSTRAINT uq_customers_email    UNIQUE (email),
    CONSTRAINT uq_customers_document UNIQUE (document)
);
