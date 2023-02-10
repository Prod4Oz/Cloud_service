create table file(
    id           bigserial not null,
    file_content oid,
    filename     varchar(255),
    size         bigint,
    upload_date  date      not null,
    primary key (id)
);

create table tokens(
    token varchar(255) not null,
    primary key (token)
);

create table users(
    id       bigserial not null,
    password varchar(255),
    username varchar(100),
    primary key (id)
);