create table file
(
    filename     varchar(255) not null,
    file_content oid,
    size         bigint,
    upload_date  timestamp(6) not null,
    primary key (filename)
);

create table tokens
(
    token varchar(255) not null,
    primary key (token)
);

create table users
(
    id       bigserial not null,
    password varchar(255),
    username varchar(100),
    primary key (id)
);