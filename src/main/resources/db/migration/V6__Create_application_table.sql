CREATE TABLE applications
(
    id     BIGINT PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    job_id BIGINT      NOT NULL,
    FOREIGN KEY (job_id) REFERENCES jobs (id)
);