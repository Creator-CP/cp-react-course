-- Очистка таблиц (опционально)
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM cart_items;
DELETE FROM carts;
DELETE FROM product_images;
DELETE FROM products;
DELETE FROM categories;
DELETE FROM users;

-- Вставка категорий
INSERT INTO categories (id, name, description) VALUES
(2, 'Компьютеры и ноутбуки', NULL),
(3, 'Смартфоны и гаджеты', NULL),
(8, 'Кухонная техника', NULL),
(9, 'Красота и здоровье', NULL);

-- Вставка товаров
INSERT INTO products (id, category_id, name, description, price, created_at, updated_at) VALUES
-- Электроника/Компьютеры
(1, 2, 'Ноутбук ASUS ROG Strix', 'Игровой ноутбук, 16 ГБ RAM, RTX 3060', 1499.99, NOW(), NOW()),
(2, 2, 'MacBook Pro 14"', 'Apple M1 Pro, 16 ГБ RAM, 512 ГБ SSD', 1999.00, NOW(), NOW()),
(3, 2, 'Игровой ПК HP Omen', 'Intel i7, 32 ГБ RAM, RTX 3080', 2499.99, NOW(), NOW()),

-- Электроника/Смартфоны
(4, 3, 'iPhone 15 Pro', '6.1", A17 Pro, 128 ГБ', 999.00, NOW(), NOW()),
(5, 3, 'Samsung Galaxy S23 Ultra', '6.8", 256 ГБ, S Pen', 1199.99, NOW(), NOW()),
(6, 3, 'Xiaomi Redmi Note 12', '6.67", 128 ГБ, AMOLED', 249.99, NOW(), NOW()),

-- Бытовая техника
(11, 8, 'Холодильник Bosch', 'No Frost, 350 л', 1299.00, NOW(), NOW()),
(12, 8, 'Кофемашина DeLonghi', 'Автоматическая капсульная', 499.99, NOW(), NOW()),

-- Красота и здоровье
(13, 9, 'Фен Dyson Supersonic', 'Профессиональный фен', 429.99, NOW(), NOW()),
(14, 9, 'Электрическая зубная щетка Oral-B', 'Smart 1500 с датчиком давления', 99.99, NOW(), NOW());

-- Изображения товаров
INSERT INTO product_images (id, product_id, image_url, is_main, `display_order`) VALUES
-- Ноутбук ASUS
(1, 1, 'http://localhost:10800/images/asus_rog_1.jpg', true, 1),
(2, 1, 'http://localhost:10800/images/asus_rog_2.jpg', false, 2),

-- MacBook Pro
(3, 2, 'http://localhost:10800/images/macbook_pro_1.png', true, 1),

-- ПК Omen
(4, 3, 'http://localhost:10800/images/omen_1.jpg', true, 1),

-- iPhone 15 Pro
(5, 4, 'http://localhost:10800/images/iphone15_1.jpg', true, 1),
(6, 4, 'http://localhost:10800/images/iphone15_2.jpg', false, 2),
(7, 4, 'http://localhost:10800/images/iphone15_3.jpg', false, 3),

-- Galaxy
(8, 5, 'http://localhost:10800/images/galaxy_1.jpg', true, 1),

-- Xiaomi
(9, 6, 'http://localhost:10800/images/xiaomi_1.jpg', true, 1),

-- Холодильник Bosch
(10, 11, 'http://localhost:10800/images/bosch_fridge_1.jpg', true, 1),

-- Кофемашина
(11, 12, 'http://localhost:10800/images/coffee.jpg', true, 1),

-- Фен Dyson
(12, 13, 'http://localhost:10800/images/dyson_hair_1.jpg', true, 1),
(13, 13, 'http://localhost:10800/images/dyson_hair_2.jpg', false, 2),

-- Oral-B
(14, 14, 'http://localhost:10800/images/oral-b_1.jpg', true, 1);
