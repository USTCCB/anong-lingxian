DROP TABLE IF EXISTS product;
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    category VARCHAR(64),
    image_url VARCHAR(256)
);
DROP TABLE IF EXISTS product_order;
CREATE TABLE product_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(16) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_order_user ON product_order(user_id);
CREATE INDEX idx_order_status ON product_order(status);
INSERT INTO product(name, price, stock, category, image_url) VALUES ('安农土鸡蛋', 38.00, 100, '蛋品', 'https://placehold.co/200');
INSERT INTO product(name, price, stock, category, image_url) VALUES ('现摘草莓 500g', 49.00, 50, '水果', 'https://placehold.co/200');
INSERT INTO product(name, price, stock, category, image_url) VALUES ('有机青菜', 12.00, 200, '蔬菜', 'https://placehold.co/200');
