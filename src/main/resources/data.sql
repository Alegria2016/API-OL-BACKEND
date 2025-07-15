-- Primero resetear las secuencias (opcional pero recomendado)
ALTER SEQUENCE user_seq RESTART START WITH 1;
ALTER SEQUENCE merchant_seq RESTART START WITH 1;
ALTER SEQUENCE establishment_seq RESTART START WITH 1;

-- Insertar usuarios usando la secuencia mediante NEXTVAL
INSERT INTO users (id, name, email, password, role) VALUES
(user_seq.NEXTVAL, 'Admin User', 'admin@example.com', '$2a$10$xS.x8qJ1L6B2h5Z0Q5b5.eX5v5X5v5X5v5X5v5X5v5X5v5X5v5X5', 'ADMIN');

INSERT INTO users (id, name, email, password, role) VALUES
(user_seq.NEXTVAL, 'Registration Assistant', 'assistant@example.com', '$2a$10$xS.x8qJ1L6B2h5Z0Q5b5.eX5v5X5v5X5v5X5v5X5v5X5v5X5v5X5', 'REGISTRATION_ASSISTANT');

-- Insertar comerciantes
INSERT INTO merchants (id, name, municipality, phone, email, registration_date, status, created_by, updated_by, creation_date, update_date) VALUES
(merchant_seq.NEXTVAL, 'Tech Solutions Inc', 'Bogotá', '1234567890', 'tech@example.com', TO_DATE('2023-01-15', 'YYYY-MM-DD'), 'ACTIVE', 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO merchants (id, name, municipality, phone, email, registration_date, status, created_by, updated_by, creation_date, update_date) VALUES
(merchant_seq.NEXTVAL, 'Green Foods', 'Medellín', NULL, 'green@example.com', TO_DATE('2023-02-20', 'YYYY-MM-DD'), 'ACTIVE', 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO merchants (id, name, municipality, phone, email, registration_date, status, created_by, updated_by, creation_date, update_date) VALUES
(merchant_seq.NEXTVAL, 'Fashion Trends', 'Cali', '9876543210', NULL, TO_DATE('2023-03-10', 'YYYY-MM-DD'), 'INACTIVE', 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO merchants (id, name, municipality, phone, email, registration_date, status, created_by, updated_by, creation_date, update_date) VALUES
(merchant_seq.NEXTVAL, 'Home Decor', 'Barranquilla', '5551234567', 'home@example.com', TO_DATE('2023-04-05', 'YYYY-MM-DD'), 'ACTIVE', 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO merchants (id, name, municipality, phone, email, registration_date, status, created_by, updated_by, creation_date, update_date) VALUES
(merchant_seq.NEXTVAL, 'Auto Parts Co', 'Cartagena', NULL, NULL, TO_DATE('2023-05-12', 'YYYY-MM-DD'), 'INACTIVE', 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

-- Insertar establecimientos
INSERT INTO establishments (id, name, income, employee_count, merchant_id, created_by, updated_by, creation_date, update_date) VALUES
(establishment_seq.NEXTVAL, 'Tech Main Store', 50000.00, 10, 1, 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO establishments (id, name, income, employee_count, merchant_id, created_by, updated_by, creation_date, update_date) VALUES
(establishment_seq.NEXTVAL, 'Tech Downtown', 30000.00, 5, 1, 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO establishments (id, name, income, employee_count, merchant_id, created_by, updated_by, creation_date, update_date) VALUES
(establishment_seq.NEXTVAL, 'Green Foods North', 45000.00, 8, 2, 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO establishments (id, name, income, employee_count, merchant_id, created_by, updated_by, creation_date, update_date) VALUES
(establishment_seq.NEXTVAL, 'Green Foods South', 38000.00, 6, 2, 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO establishments (id, name, income, employee_count, merchant_id, created_by, updated_by, creation_date, update_date) VALUES
(establishment_seq.NEXTVAL, 'Fashion Central', 28000.00, 4, 3, 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO establishments (id, name, income, employee_count, merchant_id, created_by, updated_by, creation_date, update_date) VALUES
(establishment_seq.NEXTVAL, 'Fashion Outlet', 22000.00, 3, 3, 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO establishments (id, name, income, employee_count, merchant_id, created_by, updated_by, creation_date, update_date) VALUES
(establishment_seq.NEXTVAL, 'Home Decor Main', 42000.00, 7, 4, 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO establishments (id, name, income, employee_count, merchant_id, created_by, updated_by, creation_date, update_date) VALUES
(establishment_seq.NEXTVAL, 'Home Decor Express', 35000.00, 5, 4, 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO establishments (id, name, income, employee_count, merchant_id, created_by, updated_by, creation_date, update_date) VALUES
(establishment_seq.NEXTVAL, 'Auto Parts West', 32000.00, 4, 5, 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO establishments (id, name, income, employee_count, merchant_id, created_by, updated_by, creation_date, update_date) VALUES
(establishment_seq.NEXTVAL, 'Auto Parts East', 29000.00, 3, 5, 'system', 'system', SYSTIMESTAMP, SYSTIMESTAMP);