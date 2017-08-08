--заполнение тестовыми данными
INSERT INTO "TUser" (user_login,user_password) VALUES ('user1', '123');
INSERT INTO "TUser" (user_login,user_password) VALUES ('user2', '123');

INSERT INTO "Contact" (contact_name,user_id) VALUES ('contact1',1);
INSERT INTO "Contact" (contact_name,user_id) VALUES ('contact2',1);
INSERT INTO "Contact" (contact_name,user_id) VALUES ('contact3',1);
INSERT INTO "Contact" (contact_name,user_id) VALUES ('contact4',2);
INSERT INTO "Contact" (contact_name,user_id) VALUES ('contact5',2);

INSERT INTO "Group" (group_name,user_id) VALUES ('group1',1);
INSERT INTO "Group" (group_name,user_id) VALUES ('group2',1);
INSERT INTO "Group" (group_name,user_id) VALUES ('group3',2);
INSERT INTO "Group" (group_name,user_id) VALUES ('group4',2);

INSERT INTO "Contact_group" (contact_id,group_id) VALUES (1,1);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (1,2);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (2,1);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (3,1);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (4,3);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (4,4);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (5,3);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (5,4);


INSERT INTO "ContactDetails" (details_type, details_value,contact_id) VALUES ('Phone', '8937453626', 1);
INSERT INTO "ContactDetails" (details_type, details_value,contact_id) VALUES ('Email', 'uuuu@hhhh.com', 1);
INSERT INTO "ContactDetails" (details_type, details_value,contact_id) VALUES ('Phone', '8927354367', 2);
INSERT INTO "ContactDetails" (details_type, details_value,contact_id) VALUES ('Phone', '8937786567', 3);
INSERT INTO "ContactDetails" (details_type, details_value,contact_id) VALUES ('Skype', 'nnnnn001', 3);
INSERT INTO "ContactDetails" (details_type, details_value,contact_id) VALUES ('Phone', '8297876545', 4);
INSERT INTO "ContactDetails" (details_type, details_value,contact_id) VALUES ('Email', 'nnnn@khdf.ru', 4);
INSERT INTO "ContactDetails" (details_type, details_value,contact_id) VALUES ('Phone', '8961545634', 5);
INSERT INTO "ContactDetails" (details_type, details_value,contact_id) VALUES ('Skype', 'zzzz001', 5);
