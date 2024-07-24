DROP TABLE IF EXISTS CATEGORIES;
DROP TABLE IF EXISTS PRODUCTS;
DROP TABLE IF EXISTS CUSTOMER;


CREATE TABLE IF NOT EXISTS  CATEGORIES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO CATEGORIES (name) VALUES ('Electrodomesticos');
INSERT INTO CATEGORIES (name) VALUES ('Muebles');

CREATE TABLE IF NOT EXISTS  PRODUCTS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    category_id BIGINT,
    CONSTRAINT fk_category
        FOREIGN KEY (category_id) 
        REFERENCES CATEGORIES(id)
);

INSERT INTO PRODUCTS (name, description, price, category_id) VALUES ('Labadora', 'Labadora LG ultima generacion', 3000000, 1);
INSERT INTO PRODUCTS (name, description, price, category_id) VALUES ('Nevera', 'Nevera samsung ultima generacion', 1500000, 1);
INSERT INTO PRODUCTS (name, description, price, category_id) VALUES ('Sofa', 'Sofa rojo para amplia sala', 790000, 2);

CREATE TABLE IF NOT EXISTS  INVENTORIES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    quantity INT NOT NULL,
    date DATE NOT NULL,
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
        REFERENCES PRODUCTS(id),
    CONSTRAINT unique_inventory_entry
        UNIQUE (product_id, date)
);

INSERT INTO INVENTORIES (product_id, quantity, date) VALUES (1, 50, '2024-07-24');
INSERT INTO INVENTORIES (product_id, quantity, date) VALUES (2, 30, '2024-07-24');