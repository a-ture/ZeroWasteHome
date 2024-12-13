# ZeroWasteHome

ZeroWasteHome è un'applicazione progettata per la gestione intelligente di dispensa e frigorifero. L'obiettivo è aiutare gli utenti a ridurre gli sprechi alimentari, ottimizzare la spesa e mantenere un controllo efficace sugli alimenti disponibili. Grazie all'integrazione con dispositivi IoT e altre funzionalità avanzate, ZeroWasteHome rappresenta un assistente personale per la gestione della cucina.

## Caratteristiche principali

- **Monitoraggio delle scadenze**: Ricevi notifiche per gli alimenti in scadenza e visualizza rapidamente lo stato della dispensa e del frigorifero.
- **Generazione di ricette**: Suggerimenti basati sugli alimenti in scadenza o disponibili in casa.
- **Lista della spesa intelligente**: Genera automaticamente una lista della spesa basata sugli alimenti mancanti o sulle ricette pianificate.
- **Integrazione IoT**: Collegati a dispositivi intelligenti per visualizzare in tempo reale il contenuto di frigorifero e dispensa.
- **Gestione personalizzata**: Filtra alimenti e ricette in base alle preferenze alimentari (es. vegano, senza glutine).
- **Report di utilizzo**: Statistiche sull'uso degli alimenti e sulle abitudini di consumo per migliorare la gestione domestica.

## Tecnologie utilizzate

- **Frontend**: Angular (stand-alone components, PrimeNG per l'interfaccia utente).
- **Backend**: Spring Framework con MySQL come database.
- **Database**: MySQL Workbench 8.0 per la gestione del database relazionale.
- **API esterne**: Open Food Facts per l'accesso a informazioni dettagliate sugli alimenti tramite codice a barre.
- **Test e linting**: Swagger UI per testare gli endpoint e Prettier 3.3.3 per il linting del codice.
- **IoT**: Integrazione con dispositivi IoT per monitorare il contenuto in tempo reale.

## Installazione

### Requisiti
- **Node.js** (versione 18 o superiore)
- **Angular CLI**
- **Java** (versione 17 o superiore)
- **MySQL Workbench** (versione 8.0)
- **Maven**

### Passaggi

1. **Clona il repository**:
   ```bash
   git clone https://github.com/tuo-utente/zerowastehome.git
   cd zerowastehome
   ```

2. **Configura il database**:
   - Crea un database MySQL chiamato `zerowastehome`.
   - Importa il file `schema.sql` nella directory `database`.

3. **Configura il backend**:
   - Accedi alla directory `backend`:
     ```bash
     cd backend
     ```
   - Modifica il file `application.properties` con le tue credenziali MySQL:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/zerowastehome
     spring.datasource.username=tuo-username
     spring.datasource.password=tuo-password
     ```
   - Compila ed esegui il backend:
     ```bash
     mvn spring-boot:run
     ```

4. **Configura il frontend**:
   - Accedi alla directory `frontend`:
     ```bash
     cd frontend
     ```
   - Installa le dipendenze:
     ```bash
     npm install
     ```
   - Avvia il server di sviluppo Angular:
     ```bash
     ng serve
     ```

5. **Accesso all'app**:
   - Apri il browser e visita: `http://localhost:4200`

## Struttura del progetto

- **frontend/**: Codice sorgente dell'interfaccia utente (Angular).
- **backend/**: Codice sorgente del server (Spring Framework).
- **database/**: File SQL per il setup del database.
- **docs/**: Documentazione aggiuntiva e diagrammi del progetto.

## Contributi

Contribuire a ZeroWasteHome è facile:

1. Forka il repository.
2. Crea un branch per la tua feature o fix:
   ```bash
   git checkout -b nome-feature
   ```
3. Effettua le modifiche e assicurati che il codice sia formattato con Prettier.
4. Esegui i test per garantire che tutto funzioni correttamente.
5. Fai una pull request verso il branch principale.

## Autori
Project Managers: 
- **Raffaella Spagnuolo**
- **Alessia Ture**

Team members: 
- **Giovanni Balzano**
- **Benito Farina**
- **Marco Meglio**
- **Ferdinando Ranieri**
- **Marco Renella**
- **Giuseppe Russo**
- **Anna Tagliamonte**
- **Alessandra Trotta**

