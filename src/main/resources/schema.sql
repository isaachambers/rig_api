DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    product_id INT AUTO_INCREMENT,
    name       VARCHAR(250) NOT NULL,
    author     VARCHAR(250) NOT NULL,
    quantity   INT          NOT NULL,
    PRIMARY KEY (product_id)
);

DROP TABLE IF EXISTS customer;

CREATE TABLE customer
(
    customer_id INT AUTO_INCREMENT,
    first_name  VARCHAR(250) NOT NULL,
    last_name   VARCHAR(250) NOT NULL,
    PRIMARY KEY (customer_id)
);

DROP TABLE IF EXISTS order_tbl;

CREATE TABLE order_tbl
(
    order_id    INT AUTO_INCREMENT,
    customer_id INT NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);

DROP TABLE IF EXISTS order_detail;

CREATE TABLE order_detail
(
    order_detail_id INT AUTO_INCREMENT,
    product_id      INT NOT NULL,
    order_id        INT NOT NULL,
    quantity        INT NOT NULL,
    PRIMARY KEY (order_detail_id),
    FOREIGN KEY (order_id) REFERENCES order_tbl (customer_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id)
);

