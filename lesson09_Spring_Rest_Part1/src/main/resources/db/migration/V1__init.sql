create table if not exists students (id bigserial primary key, name varchar(255), score int, secret_key varchar(255));

insert into students (name, score, secret_key)
values
('Bob', 100, 'hello'),
('Jack', 90, 'hello'),
('John', 50, 'hello'),
('John2', 50, 'hello'),
('John3', 50, 'hello'),
('John4', 50, 'hello'),
('John5', 50, 'hello'),
('John6', 50, 'hello'),
('John7', 50, 'hello'),
('John8', 50, 'hello'),
('John9', 50, 'hello'),
('John10', 50, 'hello'),
('John11', 50, 'hello'),
('John12', 50, 'hello'),
('John13', 50, 'hello'),
('John14', 50, 'hello');