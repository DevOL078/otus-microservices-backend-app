alter table users
add column if not exists encrypted_password varchar(255);
