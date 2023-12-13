--liquibase formatted sql
--changeset petar.mirchev:202312121500_1
create table test
(
    name        varchar(255),
    updated_by  varchar(255)
);
