/*

Create a user : user1
Create database : local1
assign rights to this user.

*/

create table Person (

   id int NOT NULL auto_increment,
   name varchar(100)  NOT NULL default "",
   queueStatus int NOT NULL default 0,
   verificationStatus varchar(100)  NOT NULL default "",
   initialAddition datetime  NOT NULL default "0001-01-01 00:00:00",
   queueAddition datetime  NOT NULL default "0001-01-01 00:00:00",
   PRIMARY KEY (id)

);