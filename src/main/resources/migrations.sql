--liquibase formatted sql
--author:id uniquely identifies each change set
--changeset yuva:1
CREATE TABLE IF NOT EXISTS accounts (
  id SERIAL PRIMARY KEY,
  name TEXT
);

CREATE TABLE IF NOT EXISTS journals (
  id SERIAL PRIMARY KEY,
  accountid INTEGER references accounts(Id),
  transactiontype TEXT not null,
  amount numeric(18,2),
  createdAt TIMESTAMP default current_timestamp
);

