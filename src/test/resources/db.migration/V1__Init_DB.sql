create table file
(
    filename      varchar(255) not null,
    file_content  oid,
    size          bigint,
    upload_date   timestamp(6) not null,
    user_username varchar(255),
    primary key (filename)
);

create table tokens
(
    token varchar(255) not null,
    primary key (token)
);

create table users
(
    username varchar(100) not null,
    password varchar(255),
    primary key (username)
);

create table users_user_files
(
    user_username       varchar(255) not null,
    user_files_filename varchar(255) not null
);


alter table if exists users_user_files add constraint UK_p632bulwqla736wtt3yobsrn6 unique (user_files_filename);

alter table if exists file add constraint FK5o58ene1vl23lvvg9iiq929le foreign key (user_username) references users;

alter table if exists users_user_files add constraint FKqeia8n8ej8v514i9q1e2tlw4c foreign key (user_files_filename) references file;

alter table if exists users_user_files add constraint FKh4redaoyxdoqj36vcg0bygq0e foreign key (user_username) references users;