﻿--функции для получение данных из таблиц

-- вывод контакта
CREATE OR REPLACE FUNCTION getContact(c_id INT) RETURNS SETOF "Contact" AS
$BODY$
DECLARE
  r "Contact";
BEGIN
    FOR r IN
        SELECT
              *
        FROM "Contact"
            WHERE contact_id=c_id
    LOOP
        RETURN NEXT r;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE plpgsql;


-- вывод списка контактов пользователя

CREATE OR REPLACE FUNCTION getListContact(u_id INT,find_name VARCHAR DEFAULT '') RETURNS SETOF "Contact" AS
$BODY$
DECLARE
  r "Contact";
BEGIN
    FOR r IN
        SELECT
              *
        FROM "Contact"
            WHERE user_id=u_id AND
            (find_name='' OR  (POSITION (find_name IN contact_name)>0))
            ORDER BY contact_id
    LOOP
        RETURN NEXT r;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE plpgsql;


-- вывод списка контактов группы
CREATE OR REPLACE FUNCTION getListContactsGroup(g_id INT) RETURNS SETOF "Contact" AS
$BODY$
DECLARE
  r "Contact";
BEGIN
    FOR r IN
        SELECT
              *
        FROM "Contact"
            WHERE
            contact_id IN (SELECT contact_id FROM "Contact_group" WHERE group_id=g_id)
        ORDER BY contact_id
    LOOP
        RETURN NEXT r;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE plpgsql;


-- вывод списка групп пользователя

CREATE OR REPLACE FUNCTION getListGroup(u_id INT,find_name VARCHAR DEFAULT '') RETURNS SETOF "Group" AS
$BODY$
DECLARE
  r "Group";
BEGIN
    FOR r IN
        SELECT
              *
        FROM "Group"
            WHERE user_id=u_id AND
            (find_name='' OR  (POSITION (find_name IN group_name)>0))
            ORDER BY group_id
    LOOP
        RETURN NEXT r;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE plpgsql;

-- вывод списка контактов группы
CREATE OR REPLACE FUNCTION getListGroupInContact(c_id INT) RETURNS SETOF "Group" AS
$BODY$
DECLARE
  r "Group";
BEGIN
    FOR r IN
        SELECT
              *
        FROM "Group"
            WHERE
            group_id IN (SELECT group_id FROM "Contact_group" WHERE contact_id=c_id)
        ORDER BY group_id
    LOOP
        RETURN NEXT r;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE plpgsql;

-- вывод деталей сонтакта
CREATE OR REPLACE FUNCTION getContactDetails(c_id INT) RETURNS SETOF "ContactDetails" AS
$BODY$
DECLARE
  r "ContactDetails";
BEGIN
    FOR r IN
        SELECT
              *
        FROM "ContactDetails"
            WHERE contact_id=c_id
        ORDER BY details_id
    LOOP
        RETURN NEXT r;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE plpgsql;
