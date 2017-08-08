--Функции для работы с пользователями
--Добавление нового пользователя
CREATE OR REPLACE FUNCTION InsertUser(u_login VARCHAR,u_password VARCHAR) RETURNS BOOLEAN AS
$BODY$
BEGIN
    INSERT INTO "TUser" (user_login,
                            user_password)
    	 VALUES (
         u_login,
         u_password);
         RETURN TRUE;
EXCEPTION
 WHEN unique_violation THEN
     RETURN FALSE;
END
$BODY$
LANGUAGE 'plpgsql';


--Изменение пользователя
CREATE OR REPLACE FUNCTION ChangeUser(u_id INT,u_login VARCHAR,u_password VARCHAR) RETURNS BOOLEAN AS
$BODY$
BEGIN
	UPDATE "TUser" SET user_login = u_login, user_password = u_password  WHERE user_id=u_id;
    RETURN TRUE;
EXCEPTION
 WHEN unique_violation THEN
     RETURN FALSE;
END
$BODY$
LANGUAGE 'plpgsql';

--Удаление пользователя

CREATE OR REPLACE FUNCTION DeleteUser(u_id INT) RETURNS VOID AS
$BODY$
DECLARE
id INT;
BEGIN
	FOR id IN
        SELECT
  			contact_id
		FROM "Contact"
			WHERE user_id=u_id
    LOOP
		PERFORM DeleteContact(id);
        DELETE FROM "TUser"	WHERE "TUser".user_id = d_id;
    END LOOP;
END
$BODY$
LANGUAGE 'plpgsql';

--Получение данных пользователя

-- получение пользователя, а если id не указан, то всех пользователей
CREATE OR REPLACE FUNCTION getUsers(u_id INT DEFAULT NULL) RETURNS SETOF "TUser" AS
$BODY$
DECLARE
  r "TUser";
BEGIN
	FOR r IN
        SELECT
  			*
		FROM "TUser"
			WHERE (u_id=NULL OR (user_id = u_id))
    LOOP
        RETURN NEXT r;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE plpgsql;

--------------------------
--Аналитика по пользователям

--Получает количество пользователей
CREATE OR REPLACE FUNCTION NumberOfUsers() RETURNS INT AS
$BODY$
DECLARE
i INT;
BEGIN
    SELECT count(*) FROM "TUser" INTO i;
	return i;
END;
$BODY$
LANGUAGE plpgsql;

--Получает сводные данные по всем пользователям

CREATE OR REPLACE FUNCTION CountingUserData() RETURNS TABLE(u_id INT,count_contact int,count_group int)  AS
$BODY$
DECLARE
r RECORD;
BEGIN
FOR r IN
	SELECT
		u.user_id,
		count(DISTINCT contact_id)  AS count_contact,
		count(DISTINCT group_id) AS count_group
	FROM "TUser" u
    LEFT JOIN "Contact" c ON u.user_id=c.user_id
    LEFT JOIN "Group" g ON u.user_id=g.user_id
	GROUP BY u.user_id
	LOOP
		u_id = r.user_id;
		count_contact =r.count_contact;
		count_group = r.count_group;
		RETURN next;
	END LOOP;
END;
$BODY$
LANGUAGE plpgsql;

-- считает среднее количество контактов пользователей
CREATE OR REPLACE FUNCTION AverageUserContact() RETURNS REAL AS
$BODY$
DECLARE
i REAL;
BEGIN
    SELECT avg(t.count_contact) As avg_user FROM (
    SELECT
		u.user_id as id,
		count(DISTINCT contact_id)  AS count_contact
	FROM "TUser" u
    LEFT JOIN "Contact" c ON u.user_id=c.user_id
	GROUP BY u.user_id) As t
	INTO i;
	return i;
END;
$BODY$
LANGUAGE plpgsql;

-- считает среднее количество групп пользователей
CREATE OR REPLACE FUNCTION AverageUserGroup() RETURNS REAL AS
$BODY$
DECLARE
i REAL;
BEGIN
    SELECT avg(t.count_group) As avg_user FROM (
    SELECT
		u.user_id,
		count(DISTINCT group_id)  AS count_group
	FROM "TUser" u
    LEFT JOIN "Group" c ON u.user_id=c.user_id
	GROUP BY u.user_id) As t
	INTO i;
	return i;
END;
$BODY$
LANGUAGE plpgsql;

-- получает список пользователей у которых количество контактов меньше n
CREATE OR REPLACE FUNCTION InactiveUsers(n INT) RETURNS TABLE(u_id INT,u_login VARCHAR) AS
$BODY$
DECLARE
r RECORD;
BEGIN
    FOR r IN
	SELECT * FROM
    (SELECT
		u.user_id,
        u.user_login,
		count(DISTINCT contact_id)  AS count_contact
	FROM "TUser" u
    LEFT JOIN "Contact" c ON u.user_id=c.user_id
    GROUP BY u.user_id) AS t
    WHERE count_contact<n
	LOOP
		u_id = r.user_id;
        u_login = r.user_login;
		RETURN next;
	END LOOP;
END;
$BODY$
LANGUAGE plpgsql;