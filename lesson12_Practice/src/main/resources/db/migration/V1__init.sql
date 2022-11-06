create table if not exists products
(
    id    bigserial primary key,
    title varchar(255),
    price int
);

insert into products (title, price)
values ('Milk', 100),
       ('Bread', 80),
       ('Cheese', 90);

create table users
(
    id         bigserial primary key,
    username   varchar(50)  not null,
    password   varchar(100) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table users_roles
(
    user_id  bigint not null references users (id),
    roles_id bigint not null references roles (id),
    primary key (user_id, roles_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('bob', '$2a$12$NM8ljOi78lMtt1opiKi7FuAR5U0GlctnA4qOYyoaY6D/XYyrCBBMK', 'bob_johnson@gmail.com'),
       ('john', '$2a$12$NM8ljOi78lMtt1opiKi7FuAR5U0GlctnA4qOYyoaY6D/XYyrCBBMK', 'john_johnson@gmail.com');

insert into users_roles (user_id, roles_id)
values (1, 1),
       (2, 2);

