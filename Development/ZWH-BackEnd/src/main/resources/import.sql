INSERT INTO utente (email, password, name) VALUES ('test1@example.com', '$2a$12$8u24Shf540Hr3krsiEHE5ep23BBygG5pVRIJTIjyIazaVeM2XxDG2', 'Test User 1');
INSERT INTO utente (email, password, name) VALUES ('test2@example.com', '$2a$12$8u24Shf540Hr3krsiEHE5ep23BBygG5pVRIJTIjyIazaVeM2XxDG2', 'Test User 2');

INSERT INTO utente_categoria (utente_email, categoria) VALUES ("test1@example.com", null);
INSERT INTO utente_categoria (utente_email, categoria) VALUES ("test2@example.com", null);

INSERT INTO prodotto (codice_barre, nome_prodotto) VALUES ('1', 'Latte');
INSERT INTO prodotto (codice_barre, nome_prodotto) VALUES ('2', 'Pane');
INSERT INTO prodotto (codice_barre, nome_prodotto) VALUES ('3', 'Uova');
INSERT INTO prodotto (codice_barre, nome_prodotto) VALUES ('4', 'Riso');

INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '1', '01/12/2024', 2);  -- L'utente 1 (vegano, gluten-free) ha latte con scadenza 01/12/2024
INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '2', '05/12/2024', 1);  -- L'utente 1 (vegano, gluten-free) ha pane con scadenza 05/12/2024
INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '3', '10/12/2024', 5);  -- L'utente 2 (vegetariano) ha mele con scadenza 10/12/2024
INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '4', '15/12/2024', 3);  -- L'utente 2 (vegetariano) ha riso con scadenza 15/12/2024

INSERT INTO prodotto_categoria (prodotto_codice_barre, categoria) VALUES ('1', 'VEGETARIANO');  -- Latte è vegetariano
INSERT INTO prodotto_categoria (prodotto_codice_barre, categoria) VALUES ('1', 'SENZA_GLUTINE');  -- Latte è senza glutine

INSERT INTO prodotto_categoria (prodotto_codice_barre, categoria) VALUES ('2', 'SENZA_GLUTINE');  -- Pane è senza glutine
INSERT INTO prodotto_categoria (prodotto_codice_barre, categoria) VALUES ('2', 'VEGETARIANO');  -- Pane è senza glutine
INSERT INTO prodotto_categoria (prodotto_codice_barre, categoria) VALUES ('2', 'VEGANO');  -- Pane è senza glutine

INSERT INTO prodotto_categoria (prodotto_codice_barre, categoria) VALUES ('3', 'VEGETARIANO');  -- Le uova sono vegetariane
INSERT INTO prodotto_categoria (prodotto_codice_barre, categoria) VALUES ('3', 'SENZA_GLUTINE');  -- Le uova sono senza glutine

INSERT INTO prodotto_categoria (prodotto_codice_barre, categoria) VALUES ('4', 'VEGETARIANO');  -- Riso è vegetariano
INSERT INTO prodotto_categoria (prodotto_codice_barre, categoria) VALUES ('4', 'VEGANO');  -- Riso è vegano

-- Inserimenti per i prodotti in dispensa
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '2', '05/01/2025', 2);
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '4', '15/01/2025', 1);
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '1', '10/01/2025', 3);
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '3', '20/01/2025', 5);

