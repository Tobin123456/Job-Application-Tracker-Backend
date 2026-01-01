CREATE TABLE applications (
                              id BIGINT PRIMARY KEY,
                              job_id BIGINT NOT NULL,
                              status VARCHAR(50) NOT NULL,
                              FOREIGN KEY (job_id) REFERENCES jobs(id)
);