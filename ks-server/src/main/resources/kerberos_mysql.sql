create TABLE kerberos_client_database(
    name varchar(255) primary key,
    encryptPassword blob
);

create TABLE kerberos_server_database(
    name varchar(255) primary key,
    encryptPassword blob
);