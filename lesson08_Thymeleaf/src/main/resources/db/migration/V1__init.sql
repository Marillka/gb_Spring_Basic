create table if not exists students (id bigserial primary key, name varchar(255), score int);

insert into students (name, score)
values
('Bob', 100),
('John', 90),
('Carl', 50);