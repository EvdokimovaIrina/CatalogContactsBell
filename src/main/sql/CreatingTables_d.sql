-- Создание таблиц
CREATE SEQUENCE t_user_id_seq;
CREATE SEQUENCE contact_id_seq;
CREATE SEQUENCE group_id_seq;
CREATE SEQUENCE contact_details_id_seq;

CREATE TABLE t_user (
  user_id INT DEFAULT nextval('t_user_id_seq') NOT NULL PRIMARY KEY,
  user_login VARCHAR(50) NOT NULL,
  user_password VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX user_login_idx ON t_user (user_login);

CREATE TABLE contact (
  contact_id INT DEFAULT nextval('contact_id_seq') NOT NULL PRIMARY KEY,
  contact_name VARCHAR NOT NULL,
  user_id INT,
  CONSTRAINT fk_user_contact
      FOREIGN KEY (user_id) REFERENCES "t_user" (user_id)
);

CREATE INDEX contact_name_idx ON contact (contact_name);
CREATE INDEX fk_user_contact_idx ON contact (user_id);

CREATE TABLE t_group (
  group_id INT DEFAULT nextval('group_id_seq') NOT NULL PRIMARY KEY,
  group_name VARCHAR NOT NULL,
  user_id INT,
  CONSTRAINT fk_user_group
      FOREIGN KEY (user_id) REFERENCES t_user (user_id)
);

CREATE INDEX group_name_idx ON t_group (group_name);
CREATE INDEX fk_user_group_idx ON t_group (user_id);

CREATE TABLE contact_group(
  contact_id int,
  group_id int,
  CONSTRAINT contact_group_pk PRIMARY KEY (contact_id, group_id),
  CONSTRAINT fk_contact
      FOREIGN KEY (contact_id) REFERENCES contact (contact_id),
  CONSTRAINT fk_group
      FOREIGN KEY (group_id) REFERENCES t_group (group_id)
);

CREATE INDEX fk_contact_idx ON "Contact_group" (contact_id);
CREATE INDEX fk_group_idx ON "Contact_group" (group_id);

CREATE TABLE "ContactDetails" (
  details_id INT DEFAULT nextval('contact_details_id_seq') NOT NULL PRIMARY KEY,
  details_type VARCHAR(10) NOT NULL,
  details_value VARCHAR(100) NOT NULL,
  contact_id int,
  CONSTRAINT fk_contact_d
      FOREIGN KEY (contact_id) REFERENCES "Contact" (contact_id)

);

CREATE INDEX fk_contact_d_idx ON "ContactDetails" (contact_id);