
# FXML2Kivy: Progettazione e Sviluppo di un Traduttore di File FXML in Kivy

Python è uno dei linguaggi più utilizzati e apprezzati dagli sviluppatori grazie alla sua sintassi leggibile, versatilità e vasta gamma di applicazioni. 
Tuttavia, presenta una lacuna significativa nella progettazione e nello sviluppo di interfacce grafiche (UI) rispetto ad ambienti di sviluppo drag-and-drop offerti da altri linguaggi.

Questo progetto mira a colmare questa lacuna, fornendo uno strumento che traduce i file FXML, utilizzati principalmente in Java con SceneBuilder, in codice Kivy, un framework Python dedicato allo sviluppo di applicazioni grafiche. 
Il risultato è un processo di sviluppo UI semplificato e accessibile anche a chi ha competenze limitate nella programmazione di interfacce grafiche.

## Funzionalità
- Traduzione automatica di file FXML in codice Kivy.
- Possibilità di scaricare le traduzioni come pacchetti pronti per l'esecuzione in Python.
- Gestione di una lista delle traduzioni effettuate.
- Esportazione simultanea di più file.
- Correzione di codice assistita tramite AI integrata nel programma (richiede chiave API di OpenAI).

## Requisiti
- **Java**: Versione 15 o superiore.
- **JavaFX**: Librerie necessarie per il progetto.
- **JDK**: Assicurarsi di avere una versione recente.
- **IDE consigliato**: Eclipse.

## Installazione
1. Assicurati di avere installato [Eclipse](https://www.eclipse.org/downloads/).
2. Scarica il progetto da GitHub:
3. Importa il progetto in Eclipse:
   - Apri Eclipse.
   - Vai su **File > Import > Existing Projects into Workspace**.
   - Seleziona la cartella del progetto.
4. Avvia il progetto tramite l'IDE.

## Utilizzo
Una volta avviata l'applicazione:
1. Carica un file FXML da tradurre.
2. Scegli le opzioni di esportazione desiderate (singolo file o multipli).
3. Scarica il pacchetto Kivy pronto per l'uso.
4. (Facoltativo) Inserisci la tua API di OpenAI per sfruttare funzionalità AI avanzate.
