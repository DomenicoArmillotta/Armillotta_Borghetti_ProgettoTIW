# Armillotta_Borghetti_ProgettoTIW
#**Esercizio 1: aste online**

## **1.Versione HTML pura**
Un’applicazione web consente la gestione di aste online. Gli utenti accedono tramite login e possono vendere e acquistare all’asta. La HOME page contiene due link, uno per accedere alla pagina VENDO e uno per accedere alla pagina ACQUISTO. La pagina VENDO mostra una lista delle aste create dall’utente e non ancora chiuse, una lista delle aste da lui create e chiuse e una form per creare un nuovo articolo e una nuova asta per venderlo. L'asta comprende l’articolo da mettere in vendita (codice, nome, descrizione, immagine), prezzo iniziale, rialzo minimo di ogni offerta (espresso come un numero intero di euro) e una scadenza (data e ora, es 19-04-2021 alle 24:00). La lista delle aste è ordinata per data+ora crescente. L’elenco riporta: codice e nome dell’articolo, offerta massima, tempo mancante (numero di giorni e ore) tra il momento (data ora) del login e la data e ora di chiusura dell’asta. Cliccando su un’asta compare una pagina DETTAGLIO ASTA che riporta per un’asta aperta i dati dell’asta e la lista delle offerte (nome utente, prezzo offerto, data e ora dell’offerta) ordinata per data+ora decrescente. Un bottone CHIUDI permette all’utente di chiudere l’asta se è giunta l’ora della scadenza (si ignori il caso di aste scadute ma non chiuse dall’utente). Se l’asta è chiusa, la pagina riporta i dati dell’asta, il nome dell’aggiudicatario, il prezzo finale e l’indirizzo (fisso) di spedizione dell’utente. La pagina ACQUISTO contiene una form di ricerca per parola chiave. Quando l’acquirente invia una parola chiave la pagina ACQUISTO è aggiornata e mostra un elenco di aste aperte (la cui scadenza è posteriore alla data e ora dell’invio) il cui articolo contiene la parola chiave nel nome o nella descrizione. La lista è ordinata in modo decrescente in base al tempo (numero di giorni e ore) mancante alla chiusura. Cliccando su un’asta aperta compare la pagina OFFERTA che mostra i dati dell’articolo, l’elenco delle offerte pervenute in ordine di data+ora decrescente e un campo di input per inserire la propria offerta, che deve essere superiore all’offerta massima corrente di un importo pari almeno al rialzo minimo. Dopo l’invio dell’offerta la pagina OFFERTA mostra l’elenco delle offerte aggiornate. La pagina ACQUISTO contiene anche un elenco delle offerte aggiudicate all’utente con i dati dell’articolo e il prezzo finale.

**Application Design**
![Alt text](/Miscellanee/model.png?raw=true "Model")
**Database Design**
![Alt text](/Miscellanee/Untitled_Diagram.png?raw=true "Model")

## **2.Versione con JavaScript**
Si realizzi un’applicazione client server web che estende e/o modifica le specifiche precedenti come segue:
● Dopo il login, l’intera applicazione è realizzata con un’unica pagina.
● Se l’utente accede per la prima volta l’applicazione mostra il contenuto della pagina ACQUISTO. Se l’utente ha già usato l’applicazione, questa mostra il contenuto della pagina VENDO se l’ultima azione dell’utente è stata la creazione di un’asta; altrimenti mostra il contenuto della pagina ACQUISTO con l’elenco (eventualmente vuoto) delle aste su cui l’utente ha cliccato in precedenza e che sono ancora aperte. L’informazione dell’ultima azione compiuta e delle aste visitate è memorizzata a lato client per la durata di un mese.
● Ogni interazione dell’utente è gestita senza ricaricare completamente la pagina, ma produce l’invocazione asincrona del server e l’eventuale modifica solo del contenuto da aggiornare a seguito dell’evento.




## **Dubbi risolti**
**1.** Si ipotizza che l'utente chiuda l'asta nel momento della scadenza, quindi non bisogna controllare tramite un timer la giusta chiusura
**2.** Non è necessaria la registrazione degli utenti, basta inserirli nel database
