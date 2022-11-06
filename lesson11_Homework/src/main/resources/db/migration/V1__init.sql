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
       ('ROLE_MANAGER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('user', '$2a$12$hAspwMsJPpcXs0U.gn4uWemL1JDseEnXdo.DE.Gbb8.n.uz3RAfkO', 'user@gmail.com'),
       ('manager', '$2a$12$hAspwMsJPpcXs0U.gn4uWemL1JDseEnXdo.DE.Gbb8.n.uz3RAfkO', 'manager@gmail.com'),
       ('admin', '$2a$12$hAspwMsJPpcXs0U.gn4uWemL1JDseEnXdo.DE.Gbb8.n.uz3RAfkO', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 3);

create table if not exists products
(
    id    bigserial primary key,
    title varchar(255),
    cost  int
);

insert into products (title, cost)
values ('bacon', 16),
       ('beef', 45),
       ('chicken', 50),
       ('duck', 1),
       ('ham', 17),
       ('tea', 73),
       ('soda', 49),
       ('cauliflower', 12),
       ('lamb ', 87),
       ('pike', 9),
       ('asparagus', 21),
       ('beans', 50),
       ('apricot', 17),
       ('nougat', 14),
       ('popcorn', 45),
       ('pancake', 82),
       ('apple', 432),
       ('banana', 110),
       ('apple', 240),
       ('nuts', 790);
