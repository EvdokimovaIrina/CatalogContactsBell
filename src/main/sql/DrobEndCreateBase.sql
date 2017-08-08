SELECT * FROM  "TUser" ;
SELECT * FROM  "Contact" ;

SELECT InsertUser('user6','222');

SELECT ChangeUser(1,'user6','222');

SELECT * FROM  "Contact" ;

SELECT InsertContact(1,'Иванов');


SELECT * FROM  getListGroupInContact(2) ;

SELECT * FROM  getContactDetails(1) ;

SELECT DeleteContact(1);

SELECT DeleteUser(1);