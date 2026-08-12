-- ==================================================
-- Datos de ejemplo
-- ==================================================

-- ==================================================
-- Table currency
-- ==================================================
INSERT INTO currency (name, code, symbol) VALUES ('Dólar estadounidense', 'USD', '$');
INSERT INTO currency (name, code, symbol) VALUES ('Peso mexicano', 'MXN', '$');
INSERT INTO currency (name, code, symbol) VALUES ('Euro', 'EUR', '€');
INSERT INTO currency (name, code, symbol) VALUES ('Libra esterlina', 'GBP', '£');

-- ==================================================
-- Table app_user
-- ==================================================
INSERT INTO app_user (username, first_name, last_name, email, password_hash, default_currency_id) VALUES ('admin', 'Admin', 'User', 'admin@local.com', '$2b$12$...', (SELECT id FROM currency WHERE code = 'MXN'));
INSERT INTO app_user (username, first_name, last_name, email, password_hash, default_currency_id) VALUES ('user1', 'User1', 'User1', 'user1@local.com', '$2b$12$...', (SELECT id FROM currency WHERE code = 'MXN'));
INSERT INTO app_user (username, first_name, last_name, email, password_hash, default_currency_id) VALUES ('user2', 'User2', 'User2', 'user2@local.com', '$2b$12$...', (SELECT id FROM currency WHERE code = 'MXN'));
INSERT INTO app_user (username, first_name, last_name, email, password_hash, default_currency_id) VALUES ('user3', 'User3', 'User3', 'user3@local.com', '$2b$12$...', (SELECT id FROM currency WHERE code = 'MXN'));
INSERT INTO app_user (username, first_name, last_name, email, password_hash, default_currency_id) VALUES ('user4', 'User4', 'User4', 'user4@local.com', '$2b$12$...', (SELECT id FROM currency WHERE code = 'MXN'));
INSERT INTO app_user (username, first_name, last_name, email, password_hash, default_currency_id) VALUES ('user5', 'User5', 'User5', 'user5@local.com', '$2b$12$...', (SELECT id FROM currency WHERE code = 'MXN'));

-- ==================================================
-- Table app_role
-- ==================================================
INSERT INTO app_role (name, description) VALUES ('admin', 'Administrador del sistema');
INSERT INTO app_role (name, description) VALUES ('user', 'Usuario normal');

-- ==================================================
-- Table app_user_role
-- ==================================================
INSERT INTO app_user_role (user_id, role_id) VALUES ((SELECT id FROM app_user WHERE username = 'admin'), (SELECT id FROM app_role WHERE name = 'admin'));
INSERT INTO app_user_role (user_id, role_id) VALUES ((SELECT id FROM app_user WHERE username = 'user1'), (SELECT id FROM app_role WHERE name = 'user'));
INSERT INTO app_user_role (user_id, role_id) VALUES ((SELECT id FROM app_user WHERE username = 'user2'), (SELECT id FROM app_role WHERE name = 'user'));
INSERT INTO app_user_role (user_id, role_id) VALUES ((SELECT id FROM app_user WHERE username = 'user3'), (SELECT id FROM app_role WHERE name = 'user'));
INSERT INTO app_user_role (user_id, role_id) VALUES ((SELECT id FROM app_user WHERE username = 'user4'), (SELECT id FROM app_role WHERE name = 'user'));
INSERT INTO app_user_role (user_id, role_id) VALUES ((SELECT id FROM app_user WHERE username = 'user5'), (SELECT id FROM app_role WHERE name = 'user'));

-- ==================================================
-- Table category
-- ==================================================
INSERT INTO category (name, type) VALUES ('Comida', 2);
INSERT INTO category (name, type) VALUES ('Transporte', 2);
INSERT INTO category (name, type) VALUES ('Entretenimiento', 2);
INSERT INTO category (name, type) VALUES ('Salud', 2);
INSERT INTO category (name, type) VALUES ('Educación', 2);
INSERT INTO category (name, type) VALUES ('Ropa', 2);
INSERT INTO category (name, type) VALUES ('Hogar', 2);
INSERT INTO category (name, type) VALUES ('Otros', 2);

INSERT INTO category (name, type) VALUES ('Salario', 1);
INSERT INTO category (name, type) VALUES ('Inversión', 1);
INSERT INTO category (name, type) VALUES ('Otro ingreso', 1);

-- ==================================================
-- Table payment_method
-- ==================================================
INSERT INTO payment_method (name, type, user_id) VALUES ('Efectivo', 1, (SELECT id FROM app_user WHERE username = 'user1'));
INSERT INTO payment_method (name, type, user_id) VALUES ('Efectivo', 1, (SELECT id FROM app_user WHERE username = 'user2'));
INSERT INTO payment_method (name, type, user_id) VALUES ('Efectivo', 1, (SELECT id FROM app_user WHERE username = 'user3'));
INSERT INTO payment_method (name, type, user_id) VALUES ('Efectivo', 1, (SELECT id FROM app_user WHERE username = 'user4'));
INSERT INTO payment_method (name, type, user_id) VALUES ('Efectivo', 1, (SELECT id FROM app_user WHERE username = 'user5'));

INSERT INTO payment_method (name, type, user_id) VALUES ('Tarjeta débito', 2, (SELECT id FROM app_user WHERE username = 'user1'));
INSERT INTO payment_method (name, type, user_id) VALUES ('Tarjeta débito', 2, (SELECT id FROM app_user WHERE username = 'user2'));
INSERT INTO payment_method (name, type, user_id) VALUES ('Tarjeta débito', 2, (SELECT id FROM app_user WHERE username = 'user3'));
INSERT INTO payment_method (name, type, user_id) VALUES ('Tarjeta débito', 2, (SELECT id FROM app_user WHERE username = 'user4'));
INSERT INTO payment_method (name, type, user_id) VALUES ('Tarjeta débito', 2, (SELECT id FROM app_user WHERE username = 'user5'));

-- ==================================================
-- Table financial_transaction
-- ==================================================
INSERT INTO financial_transaction (name, description, user_id, currency_id, payment_method_id, category_id, amount, transaction_at, created_by) VALUES ('Autobús', 'Transporte', (SELECT id FROM app_user WHERE username = 'user1'), (SELECT id FROM currency WHERE code = 'MXN'), (SELECT id FROM payment_method WHERE name = 'Efectivo' AND user_id = (SELECT id FROM app_user WHERE username = 'user1')), (SELECT id FROM category WHERE name = 'Transporte' AND type = 2), 250.00, NOW(), (SELECT id FROM app_user WHERE username = 'user1'));
INSERT INTO financial_transaction (name, description, user_id, currency_id, payment_method_id, category_id, amount, transaction_at, created_by) VALUES ('Comida', 'Comida', (SELECT id FROM app_user WHERE username = 'user1'), (SELECT id FROM currency WHERE code = 'MXN'), (SELECT id FROM payment_method WHERE name = 'Efectivo' AND user_id = (SELECT id FROM app_user WHERE username = 'user1')), (SELECT id FROM category WHERE name = 'Comida' AND type = 2), 100.00, NOW(), (SELECT id FROM app_user WHERE username = 'user1'));
INSERT INTO financial_transaction (name, description, user_id, currency_id, payment_method_id, category_id, amount, transaction_at, created_by) VALUES ('Jugo de mango', 'Jugo', (SELECT id FROM app_user WHERE username = 'user1'), (SELECT id FROM currency WHERE code = 'MXN'), (SELECT id FROM payment_method WHERE name = 'Efectivo' AND user_id = (SELECT id FROM app_user WHERE username = 'user1')), (SELECT id FROM category WHERE name = 'Comida' AND type = 2), 25.00, NOW(), (SELECT id FROM app_user WHERE username = 'user1'));
INSERT INTO financial_transaction (name, description, user_id, currency_id, payment_method_id, category_id, amount, transaction_at, created_by) VALUES ('Camisa', 'Ropa', (SELECT id FROM app_user WHERE username = 'user1'), (SELECT id FROM currency WHERE code = 'MXN'), (SELECT id FROM payment_method WHERE name = 'Efectivo' AND user_id = (SELECT id FROM app_user WHERE username = 'user1')), (SELECT id FROM category WHERE name = 'Ropa' AND type = 2), 300.00, NOW(), (SELECT id FROM app_user WHERE username = 'user1'));
INSERT INTO financial_transaction (name, description, user_id, currency_id, payment_method_id, category_id, amount, transaction_at, created_by) VALUES ('Pantalón', 'Ropa', (SELECT id FROM app_user WHERE username = 'user1'), (SELECT id FROM currency WHERE code = 'MXN'), (SELECT id FROM payment_method WHERE name = 'Efectivo' AND user_id = (SELECT id FROM app_user WHERE username = 'user1')), (SELECT id FROM category WHERE name = 'Ropa' AND type = 2), 600.00, NOW(), (SELECT id FROM app_user WHERE username = 'user1'));
INSERT INTO financial_transaction (name, description, user_id, currency_id, payment_method_id, category_id, amount, transaction_at, created_by) VALUES ('Playera', 'Ropa', (SELECT id FROM app_user WHERE username = 'user1'), (SELECT id FROM currency WHERE code = 'MXN'), (SELECT id FROM payment_method WHERE name = 'Efectivo' AND user_id = (SELECT id FROM app_user WHERE username = 'user1')), (SELECT id FROM category WHERE name = 'Ropa' AND type = 2), 320.00, NOW(), (SELECT id FROM app_user WHERE username = 'user1'));
INSERT INTO financial_transaction (name, description, user_id, currency_id, payment_method_id, category_id, amount, transaction_at, created_by) VALUES ('Sueter', 'Ropa', (SELECT id FROM app_user WHERE username = 'user1'), (SELECT id FROM currency WHERE code = 'MXN'), (SELECT id FROM payment_method WHERE name = 'Efectivo' AND user_id = (SELECT id FROM app_user WHERE username = 'user1')), (SELECT id FROM category WHERE name = 'Ropa' AND type = 2), 630.00, NOW(), (SELECT id FROM app_user WHERE username = 'user1'));
INSERT INTO financial_transaction (name, description, user_id, currency_id, payment_method_id, category_id, amount, transaction_at, created_by) VALUES ('Almacenamiento en la nube', 'Almacenamiento', (SELECT id FROM app_user WHERE username = 'user1'), (SELECT id FROM currency WHERE code = 'USD'), (SELECT id FROM payment_method WHERE name = 'Tarjeta débito' AND user_id = (SELECT id FROM app_user WHERE username = 'user1')), (SELECT id FROM category WHERE name = 'Otros' AND type = 2), 9.99, NOW(), (SELECT id FROM app_user WHERE username = 'user1'));
INSERT INTO financial_transaction (name, description, user_id, currency_id, payment_method_id, category_id, amount, transaction_at, created_by) VALUES ('Netflix', 'Netflix', (SELECT id FROM app_user WHERE username = 'user1'), (SELECT id FROM currency WHERE code = 'USD'), (SELECT id FROM payment_method WHERE name = 'Tarjeta débito' AND user_id = (SELECT id FROM app_user WHERE username = 'user1')), (SELECT id FROM category WHERE name = 'Entretenimiento' AND type = 2), 19.99, NOW(), (SELECT id FROM app_user WHERE username = 'user1'));
