create table file (
    id          bigserial not null,
    key         varchar(255),
    name        varchar(255),
    size        bigint,
    upload_date date,
    primary key (id)
);
create table tokens (
    token varchar(255) not null,
    primary key (token)
);

create table users (
    id       bigserial not null,
    password varchar(2048)not null,
    username varchar(100)not null,
    primary key (id)
);