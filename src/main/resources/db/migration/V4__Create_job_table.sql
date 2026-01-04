CREATE TABLE jobs
(
    id         BIGINT PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    url        VARCHAR(255),
    company_id BIGINT       NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies (id)
);