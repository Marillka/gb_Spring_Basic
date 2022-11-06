create table if not exists products (id bigserial primary key, title varchar(255), cost int);

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