INSERT INTO utente (email, password, name) VALUES ('test1@example.com', '$2a$12$8u24Shf540Hr3krsiEHE5ep23BBygG5pVRIJTIjyIazaVeM2XxDG2', 'Test User 1');
INSERT INTO utente (email, password, name) VALUES ('test2@example.com', '$2a$12$8u24Shf540Hr3krsiEHE5ep23BBygG5pVRIJTIjyIazaVeM2XxDG2', 'Test User 2');

INSERT INTO utente_categoria (utente_email, categoria) VALUES ("test1@example.com", null);
INSERT INTO utente_categoria (utente_email, categoria) VALUES ("test2@example.com", null);

INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('8002670500516', 'Bevanda di riso 1000 ml', 'https://images.openfoodfacts.org/images/products/800/267/050/0516/front_it.48.400.jpg');
INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('5013683305466', 'Marinated Tofu 160 g', 'https://images.openfoodfacts.org/images/products/501/368/330/5466/front_en.45.400.jpg');
INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('8002790048554', 'Uova biologiche 6 uova', 'https://images.openfoodfacts.org/images/products/800/279/004/8554/front_it.37.400.jpg');
INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('8076809518581', 'Campagnole con farina di riso', 'https://images.openfoodfacts.org/images/products/807/680/951/8581/front_it.35.400.jpg');

INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '2', '21/12/2024', 2);
INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '3', '22/12/2024', 1);
INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '2', '19/12/2024', 5);
INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '3', '27/12/2024', 3);

INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('1', 'VEGANO');  -- Latte di riso è vegano
INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('1', 'VEGETARIANO');  -- Latte di riso è vegetariano
INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('1', 'SENZA_GLUTINE');  -- Latte di riso è senza glutine

INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('2', 'VEGETARIANO');  -- Tofu è vegetariano
INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('2', 'VEGANO');  -- Tofu è senza glutine

INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('3', 'VEGETARIANO');  -- Le uova sono vegetariane

INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('4', 'VEGETARIANO');  -- Campagnole è vegetariano

-- Inserimenti per i prodotti in dispensa
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '1', '05/01/2025', 2);
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '4', '15/01/2025', 1);
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '1', '10/01/2025', 3);
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '4', '20/01/2025', 5);

