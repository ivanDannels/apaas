CREATE TABLE IF NOT EXISTS job_entities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    cron_expression VARCHAR(255)
);