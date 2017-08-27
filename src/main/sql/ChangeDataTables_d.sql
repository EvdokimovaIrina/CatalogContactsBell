----------------------------
--Функции для занесение данных в таблицы
-- Добавление нового контакта

CREATE OR REPLACE FUNCTION InsertContact(u_id INT,c_name VARCHAR) RETURNS VOID AS
$BODY$
BEGIN
    INSERT INTO contact ( contact_name,
                            user_id)
         VALUES (
         c_name,
         u_id)
         ;
END
$BODY$
LANGUAGE 'plpgsql';

-- добавление детальных данных контакта
CREATE OR REPLACE FUNCTION InsertContactDetails(c_id INT,d_type VARCHAR,d_value VARCHAR) RETURNS VOID AS
$BODY$
BEGIN
    INSERT INTO contactDetails (  details_type,
                                    details_value,
                                    contact_id)
            VALUES (
                d_type,
                d_value,
                c_id);
END
$BODY$
LANGUAGE 'plpgsql';


-- Добавление новой группы

CREATE OR REPLACE FUNCTION InsertGroup(u_id INT,g_name VARCHAR) RETURNS VOID AS
$BODY$
BEGIN
    INSERT INTO t_group (
                         group_name,
                            user_id)
         VALUES (
         g_name,
         u_id)
         ;
END
$BODY$
LANGUAGE 'plpgsql';


--Добавление группы у контакта

CREATE OR REPLACE FUNCTION InsertGroupInContact(c_id INT,g_id INT) RETURNS VOID AS
$BODY$
BEGIN
    INSERT INTO contact_group (contact_id,group_id)
                         VALUES (c_id,g_id);
END
$BODY$
LANGUAGE 'plpgsql';


--Изменение контакта
CREATE OR REPLACE FUNCTION ChangeContact(c_id INT,c_name VARCHAR) RETURNS VOID AS
$BODY$
BEGIN
UPDATE contact SET contact_name = c_name WHERE contact_id=c_id;
END
$BODY$
LANGUAGE 'plpgsql';

-- Изменение группы
CREATE OR REPLACE FUNCTION ChangeGroup(g_id INT,g_name VARCHAR) RETURNS VOID AS
$BODY$
BEGIN
UPDATE t_group SET group_name = g_name WHERE group_id=g_id;
END
$BODY$
LANGUAGE 'plpgsql';

--изменение контактной информации
CREATE OR REPLACE FUNCTION ChangeContactDetails(d_id INT,d_type VARCHAR,d_value VARCHAR) RETURNS VOID AS
$BODY$
BEGIN
UPDATE contactDetails
    SET
     details_type = d_type,
    details_value = d_value
      WHERE details_id=d_id;
END
$BODY$
LANGUAGE 'plpgsql';

--Удаление детальных данных контакта

CREATE OR REPLACE FUNCTION DeleteContactDetails(d_id INT) RETURNS VOID AS
$BODY$
BEGIN
    DELETE FROM contactDetails
          WHERE details_id = d_id;
END
$BODY$
LANGUAGE 'plpgsql';


--Удаление группs из контакта

CREATE OR REPLACE FUNCTION DeleteGroupFromContact(g_id INT,c_id INT) RETURNS VOID AS
$BODY$
BEGIN
    DELETE FROM contact_group
          WHERE group_id = g_id AND
         		 contact_id = c_id;
END
$BODY$
LANGUAGE 'plpgsql';

--Удаление контакта

CREATE OR REPLACE FUNCTION DeleteContact(c_id INT) RETURNS VOID AS
$BODY$
BEGIN
    DELETE FROM contact
          WHERE contact_id = c_id;
END
$BODY$
LANGUAGE 'plpgsql';

--Удаление группы

CREATE OR REPLACE FUNCTION DeleteGroup(g_id INT) RETURNS VOID AS
$BODY$
BEGIN
    DELETE FROM t_group
          WHERE group_id = g_id;
END
$BODY$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION DeleteDataContact() RETURNS TRIGGER AS
$BODY$
BEGIN
    DELETE FROM contactDetails
          WHERE contact_id = OLD.contact_id;
    DELETE FROM Contact_group
          WHERE contact_id = OLD.contact_id;
          RETURN OLD;
END
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER trig_delete_contact
  BEFORE DELETE ON contact FOR EACH ROW EXECUTE PROCEDURE DeleteDataContact();

CREATE OR REPLACE FUNCTION DeleteDataContact() RETURNS TRIGGER AS
$BODY$
BEGIN
    DELETE FROM contactDetails
          WHERE contact_id = OLD.contact_id;
    DELETE FROM contact_group
          WHERE contact_id = OLD.contact_id;
          RETURN OLD;
END
$BODY$
LANGUAGE 'plpgsql';

CREATE TRIGGER trig_delete_contact
  BEFORE DELETE ON contact FOR EACH ROW EXECUTE PROCEDURE DeleteDataContact();
