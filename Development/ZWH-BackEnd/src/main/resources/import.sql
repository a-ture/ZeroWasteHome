INSERT INTO utente (email, password, name) VALUES ('test1@example.com', '$2a$12$8u24Shf540Hr3krsiEHE5ep23BBygG5pVRIJTIjyIazaVeM2XxDG2', 'Test User 1');
INSERT INTO utente (email, password, name) VALUES ('test2@example.com', '$2a$12$8u24Shf540Hr3krsiEHE5ep23BBygG5pVRIJTIjyIazaVeM2XxDG2', 'Test User 2');

INSERT INTO utente_categoria (utente_email, categoria) VALUES ("test1@example.com", null);
INSERT INTO utente_categoria (utente_email, categoria) VALUES ("test2@example.com", null);

INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('8002670500516', 'Bevanda di riso 1000 ml', 'https://images.openfoodfacts.org/images/products/800/267/050/0516/front_it.48.400.jpg');
INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('5013683305466', 'Marinated Tofu 160 g', 'https://images.openfoodfacts.org/images/products/501/368/330/5466/front_en.45.400.jpg');
INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('8002790048554', 'Uova biologiche 6 uova', 'https://images.openfoodfacts.org/images/products/800/279/004/8554/front_it.37.400.jpg');
INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('8076809518581', 'Campagnole con farina di riso', 'https://images.openfoodfacts.org/images/products/807/680/951/8581/front_it.35.400.jpg');
INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('8008343200660', 'Penne Rigate N66 500g', 'https://images.openfoodfacts.org/images/products/800/834/320/0660/front_en.76.400.jpg');
INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('3017620422003', 'Nutella 400 g', 'https://images.openfoodfacts.org/images/products/301/762/042/2003/front_en.633.400.jpg');
INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('8076809579476', 'Pancake 280g', 'https://images.openfoodfacts.org/images/products/807/680/957/9476/front_it.10.400.jpg');
INSERT INTO prodotto (codice_barre, nome_prodotto, img) VALUES ('8019314000950', 'Bastoncini di tofu alle verdure 160 g', 'https://images.openfoodfacts.org/images/products/801/931/400/0950/front_it.34.400.jpg');

INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '2', '21/12/2024', 2);
INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '3', '22/12/2024', 1);
INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '2', '19/12/2024', 5);
INSERT INTO possiede_in_frigo (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '3', '27/12/2024', 3);

INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('1', 'VEGANO');  -- Latte di riso è vegano
INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('1', 'VEGETARIANO');  -- Latte di riso è vegetariano
INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('1', 'SENZA_GLUTINE');  -- Latte di riso è senza glutine

INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('2', 'VEGETARIANO');  -- Tofu è vegetariano
INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('2', 'VEGANO');  -- Tofu è vegano

INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('3', 'VEGETARIANO');  -- Le uova sono vegetariane

INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('4', 'VEGETARIANO');  -- Campagnole è vegetariano

INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('5', 'VEGETARIANO');  -- Pasta è vegetariana
INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('5', 'VEGANO');  -- Pasta è vegana

INSERT INTO prodotto_categoria (prodotto_id, categoria) VALUES ('6', 'SENZA_GLUTINE');  -- Nutella è senza glutine

-- Inserimenti per i prodotti in dispensa
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '1', '05/01/2025', 2);
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '1', '10/01/2025', 3);
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '5', '25/01/2025', 2);
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test2@example.com", '6', '25/01/2025', 2);
INSERT INTO possiede_in_dispensa (utente_email, prodotto_id, data_scadenza, quantita) VALUES ("test1@example.com", '7', '12/01/2025', 1);

INSERT INTO segnalazione_pagamento (`data_creazione`, `descrizione_problema`, `utente_email`, `stato`) VALUES ('2024-12-12', "ho un problema nel pagamento dell'abbonamento", 'test1@example.com', 'APERTA');
INSERT INTO segnalazione_pagamento (`data_creazione`, `descrizione_problema`, `utente_email`, `stato`) VALUES ('2024-12-18', "non riesco a disattivare il mio abbonamento", 'test2@example.com', 'APERTA');

INSERT INTO ZeroWasteHome.ricetta (quantita_per_persona, nome, istruzioni, img, utente_email, categoria) VALUES ('2', 'PANNA MONTATA', 'Versa la panna in una ciotola e monta con una frusta fino a ottenere una consistenza ferma.', 'panna_montata.jpg', 'test1@example.com', 'DOLCE');
INSERT INTO ZeroWasteHome.ricetta (quantita_per_persona, nome, istruzioni, img, utente_email, categoria) VALUES ('1', 'PASTA AL POMODORO', 'Cuoci la pasta in acqua salata e aggiungi il sugo di pomodoro precedentemente preparato.', 'pasta_pomodoro.jpg', 'test2@example.com', 'PRIMO');
INSERT INTO ZeroWasteHome.ricetta (quantita_per_persona, nome, istruzioni, img, utente_email, categoria) VALUES ('3', 'INSALATA MISTA', 'Lava la lattuga, taglia i pomodori e aggiungi mais e carote grattugiate. Condisci con olio e limone.', 'insalata_mista.jpg', 'test1@example.com', 'CONTORNO');
INSERT INTO ZeroWasteHome.ricetta (quantita_per_persona, nome, istruzioni, img, utente_email, categoria) VALUES ('4', 'FRITTATA DI SPINACI', "Sbatti le uova, aggiungi sale e spinaci cotti. Cuoci in padella con un filo d'olio.", 'frittata_spinaci.jpg', 'test2@example.com', 'SECONDO');
INSERT INTO ZeroWasteHome.ricetta (quantita_per_persona, nome, istruzioni, img, utente_email, categoria) VALUES ('6', 'TIRAMISÙ CLASSICO', "Alterna strati di savoiardi imbevuti di caffè con crema al mascarpone e spolvera con cacao.", 'tiramisu_classico.jpg', 'test1@example.com', 'DOLCE');
INSERT INTO ZeroWasteHome.ricetta (quantita_per_persona, nome, istruzioni, img, utente_email, categoria) VALUES ('2', 'ZUPPA DI VERDURE', 'Cuoci le verdure a pezzi (patate, carote, zucchine) in brodo vegetale per 30 minuti.', 'zuppa_verdure.jpg', 'test2@example.com', 'PRIMO');
INSERT INTO ZeroWasteHome.ricetta (quantita_per_persona, nome, istruzioni, img, utente_email, categoria) VALUES ('8', 'PIZZA MARGHERITA', "Prepara l'impasto, aggiungi pomodoro e mozzarella. Inforna a 220°C per 15 minuti.", 'pizza_margherita.jpg', 'test1@example.com', 'ANTIPASTO');

INSERT INTO gestore_community (email, password, nome) VALUES ('gestorecommunity@example.com', '$2a$12$8u24Shf540Hr3krsiEHE5ep23BBygG5pVRIJTIjyIazaVeM2XxDG2', 'Gestore Community');
INSERT INTO gestore_pagamenti (email, password, nome) VALUES ('gestorepagamenti@example.com', '$2a$12$8u24Shf540Hr3krsiEHE5ep23BBygG5pVRIJTIjyIazaVeM2XxDG2', 'Gestore Pagamenti');

INSERT INTO segnalazione_ricetta (`stato`, `ricetta_id`, `contenuto`) VALUES ('0', '1', 'La ricetta ha un contenuto inappropriato');
INSERT INTO segnalazione_ricetta (`stato`, `ricetta_id`, `contenuto`) VALUES ('0', '2', 'La ricetta ha un contenuto inappropriato');
INSERT INTO segnalazione_ricetta (`stato`, `ricetta_id`, `contenuto`) VALUES ('0', '3', 'La ricetta ha un contenuto inappropriato');