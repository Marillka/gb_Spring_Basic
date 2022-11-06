create table users
(
    id       bigserial,
    username varchar(30) not null unique,
    password varchar(60) not null,
    email    varchar(50) unique,
    primary key (id)
);

create table roles
(
    id   serial,
    name varchar(50) not null,
    primary key (id)
);

create table users_roles
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('user', '$2a$12$hAspwMsJPpcXs0U.gn4uWemL1JDseEnXdo.DE.Gbb8.n.uz3RAfkO', 'user@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1);