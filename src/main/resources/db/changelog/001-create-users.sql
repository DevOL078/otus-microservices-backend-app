create table users (
    id bigserial primary key,
    username varchar(255) not null,
    first_name varchar(255),
    last_name varchar(255),
    email varchar(255),
    phone varchar(255)
);