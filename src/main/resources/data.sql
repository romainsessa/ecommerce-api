DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
 
CREATE TABLE products (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  cost INT(250) NOT NULL
);
 
INSERT INTO products (name, description, cost) VALUES
  ('iphone', 'smartphone apple', 1000),
  ('ipad', 'tablette apple', 500),
  ('imac', 'ordinateur apple', 2000);

 CREATE TABLE categories (
  category_id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

INSERT INTO categories (name) VALUES
  ('smartphone'),
  ('tablette'),
  ('ordinateur'),
  ('apple');
  
CREATE TABLE category_product (
	category_id INT NOT NULL,
	product_id INT NOT NULL
);

INSERT INTO category_product (category_id, product_id) VALUES
	(1,1),
	(2,2),
	(3,3),
	(4,1),
	(4,2),
	(4,3);
	