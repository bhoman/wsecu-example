DROP table users if exists;

create table users (
	id integer primary key,
	username varchar(255) not null,
	name varchar(255) not null,
	email varchar(255) not null
);

