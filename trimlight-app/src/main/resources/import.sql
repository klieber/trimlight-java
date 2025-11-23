--CREATE TABLE IF NOT EXISTS schedule (
--    id BIGINT PRIMARY KEY AUTO_INCREMENT,
--    enabled BOOLEAN NOT NULL,
--    startTime TIME NOT NULL,
--    endTime TIME NOT NULL,
--    effectId INT NOT NULL
--);

INSERT INTO schedule (id, enabled, startTime, endTime, effectId)
VALUES (nextval('schedule_SEQ'), true, '17:00:00', '23:30:00', 1);

--
--create table schedule (id bigint not null, effectId integer not null, enabled boolean not null, endTime time(0), startTime time(0), primary key (id));
--create sequence schedule_SEQ start with 1 increment by 50;
