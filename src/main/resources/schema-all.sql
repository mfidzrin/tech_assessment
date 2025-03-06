DROP TABLE IF EXISTS transaction_order;

CREATE TABLE transaction_order (
    id SERIAL PRIMARY KEY,
    account_no VARCHAR,
    transaction_amount VARCHAR,
    description VARCHAR(255),
    transaction_date VARCHAR,
    transaction_time VARCHAR,
    customer_id VARCHAR,
    version BIGINT DEFAULT 0
);