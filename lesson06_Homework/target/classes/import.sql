drop table products if exists;
create table if not exists products (id bigserial primary key, title varchar(255), cost int);
insert into products (title, cost) values ('bacon', 16),('beef', 45),('chicken', 50),('duck', 1),('ham', 17),('tea', 73),('soda', 49),('cauliflower', 12),('lamb', 87),('pike', 9),('asparagus', 21),('beans', 50),('apricot', 17),('nougat', 14),('popcorn', 45),('pancake', 82);

drop table customers if exists;
create table if not exists customers (id bigserial primary key, name varchar(255));
insert into customers(name) values ('Eric'), ('Howard'), ('Steven'), ('William');

drop table orders if exists;
create table if not exists orders (id bigserial primary key, date varchar(255), product_id bigint references products (id), customer_id bigint references customers (id));
insert into orders (date, customer_id, product_id) values ('01-01-2022', 1, 1),('02-02-2022', 1, 2),('03-01-2022', 1, 3),('04-01-2022', 2, 4),('05-01-2022', 2, 5),('06-01-2022',2 ,6),('07-01-2022', 3, 7),('08-01-2022',3 ,8),('09-01-2022',3, 9);