CREATE TABLE "User_catalog" (
  user_id INT NOT NULL PRIMARY KEY,
  user_login VARCHAR(50) NOT NULL,
  user_password VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX user_login_idx ON "User_catalog" (user_login);

CREATE TABLE "Contact" (
  contact_id INT NOT NULL PRIMARY KEY,
  contact_name VARCHAR NOT NULL,
  user_id INT,
  CONSTRAINT fk_user_contact
      FOREIGN KEY (user_id) REFERENCES "User_catalog" (user_id)
);

CREATE UNIQUE INDEX contact_name_idx ON "Contact" (contact_name);

CREATE TABLE "Group" (
  group_id INT NOT NULL PRIMARY KEY,
  group_name VARCHAR NOT NULL,
  user_id INT,
  CONSTRAINT fk_user_group
      FOREIGN KEY (user_id) REFERENCES "User_catalog" (user_id)
);

CREATE UNIQUE INDEX group_name_idx ON "Group" (group_name);

CREATE TABLE "Contact_group"(
  contact_id int,
  group_id int,
  CONSTRAINT contact_group_pk PRIMARY KEY (contact_id, group_id),
  CONSTRAINT fk_contact
      FOREIGN KEY (contact_id) REFERENCES "Contact" (contact_id),
  CONSTRAINT fk_group
      FOREIGN KEY (group_id) REFERENCES "Group" (group_id)
);


CREATE TABLE "ContactDetails" (
  details_type VARCHAR(10) NOT NULL,
  details_value VARCHAR(100) NOT NULL,
  contact_id int,
  CONSTRAINT details_t_v_pk PRIMARY KEY (details_type, details_value),
  CONSTRAINT fk_contact
      FOREIGN KEY (contact_id) REFERENCES "Contact" (contact_id)

);
