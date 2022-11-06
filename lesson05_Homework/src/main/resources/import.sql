DROP TABLE products IF EXISTS;
CREATE TABLE IF NOT EXISTS products (id bigserial, title VARCHAR(255), price integer, PRIMARY KEY (id));
INSERT INTO products (title, price) VALUES ('Apple', 10), ('Orange', 20), ('Banana', 30);
