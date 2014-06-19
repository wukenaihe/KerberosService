create TABLE kerberos_client_database(
    name varchar(255) primary key,
    encryptPassword blob
);

create TABLE kerberos_server_database(
	keyName varchar(255) primary key,
    name varchar(255),
    encryptPassword blob
);

insert into kerberos_server_database(keyName) values('Name');