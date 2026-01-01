CREATE TABLE jobs (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    company_id BIGINT NOT NULL,
    url VARCHAR(255),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);