--заполнение тестовыми данными
INSERT INTO "TUser" (user_id, user_login,user_password) VALUES (1, 'user1', '123');
INSERT INTO "TUser" (user_id, user_login,user_password) VALUES (2, 'user2', '123');

INSERT INTO "Contact" (contact_id,contact_name,user_id) VALUES (1, 'contact1',1);
INSERT INTO "Contact" (contact_id,contact_name,user_id) VALUES (2, 'contact2',1);
INSERT INTO "Contact" (contact_id,contact_name,user_id) VALUES (3, 'contact3',1);
INSERT INTO "Contact" (contact_id,contact_name,user_id) VALUES (4, 'contact4',2);
INSERT INTO "Contact" (contact_id,contact_name,user_id) VALUES (5, 'contact5',2);

INSERT INTO "Group" (group_id,group_name,user_id) VALUES (1, 'group1',1);
INSERT INTO "Group" (group_id,group_name,user_id) VALUES (2, 'group2',1);
INSERT INTO "Group" (group_id,group_name,user_id) VALUES (3, 'group3',2);
INSERT INTO "Group" (group_id,group_name,user_id) VALUES (4, 'group4',2);

INSERT INTO "Contact_group" (contact_id,group_id) VALUES (1,1);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (1,2);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (2,1);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (3,1);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (4,3);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (4,4);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (5,3);
INSERT INTO "Contact_group" (contact_id,group_id) VALUES (5,4);


INSERT INTO "ContactDetails" (details_id,details_type, details_value,contact_id) VALUES (1,'Phone', '8937453626', 1);
INSERT INTO "ContactDetails" (details_id,details_type, details_value,contact_id) VALUES (2,'Email', 'uuuu@hhhh.com', 1);
INSERT INTO "ContactDetails" (details_id,details_type, details_value,contact_id) VALUES (3,'Phone', '8927354367', 2);
INSERT INTO "ContactDetails" (details_id,details_type, details_value,contact_id) VALUES (4,'Phone', '8937786567', 3);
INSERT INTO "ContactDetails" (details_id,details_type, details_value,contact_id) VALUES (5,'Skype', 'nnnnn001', 3);
INSERT INTO "ContactDetails" (details_id,details_type, details_value,contact_id) VALUES (6,'Phone', '8297876545', 4);
INSERT INTO "ContactDetails" (details_id,details_type, details_value,contact_id) VALUES (7,'Email', 'nnnn@khdf.ru', 4);
INSERT INTO "ContactDetails" (details_id,details_type, details_value,contact_id) VALUES (8,'Phone', '8961545634', 5);
INSERT INTO "ContactDetails" (details_id,details_type, details_value,contact_id) VALUES (9,'Skype', 'zzzz001', 5);
