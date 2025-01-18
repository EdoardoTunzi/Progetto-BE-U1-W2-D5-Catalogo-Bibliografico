package org.example;

import com.github.javafaker.Faker;
import org.example.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Faker faker = new Faker();
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    //-----------------------------
    static List<ElementoCatalogo> catalogoIniziale = new ArrayList<>();
    static Archivio archivio = new Archivio(catalogoIniziale);

    public static void main(String[] args) {

        inizializzaCatalogo();
        boolean condizioneDiUscita = true;

        while (condizioneDiUscita) {
            try {
                System.out.println("-------------------------------------------------");
                System.out.println("Che operazione vuoi eseguire? Digita il numero corrispondente.");
                System.out.println("1- Aggiungi elemento a catalogo");
                System.out.println("2- Cerca elemento per ISBN");
                System.out.println("3- Cerca elemento per Anno di pubblicazione");
                System.out.println("4- Cerca per Autore");
                System.out.println("5- Rimuovi da ISBN");
                System.out.println("6- Aggiorna elemento da ISBN");
                System.out.println("7- Statistiche del catalogo");
                System.out.println("8- Vedi tutti gli elementi in catalogo");
                System.out.println("9- Termina");
                int selezione = Integer.parseInt(scanner.nextLine());


                switch (selezione) {
                    case 1:
                        addElemento(archivio);
                        break;
                    case 2:
                        System.out.println("Inserisci ISBN da cercare:");
                        String isbn = scanner.nextLine();
                        archivio.ricercaDaISBN(isbn);
                        break;
                    case 3:
                        System.out.println("Inserisci l'anno da cercare:");
                        int anno = Integer.parseInt(scanner.nextLine());
                        archivio.ricercaPerAnno(anno);
                        break;
                    case 4:
                        System.out.println("Inserisci il nome dell'autore da cercare:");
                        String autore = scanner.nextLine();
                        archivio.ricercaPerAutore(autore);
                        break;
                    case 5:
                        System.out.println("Inserisci ISBN dell'elemento da rimuovere");
                        String isbn2 = scanner.nextLine();
                        archivio.rimuoviElementoDaISBN(isbn2);
                        break;
                    case 6:
                        System.out.println("Inserisci ISBN dell'elemento da aggiornare");
                        String isbn3 = scanner.nextLine();
                        aggiornaElemDaIsbn(isbn3);
                        break;

                    case 7:
                        archivio.stampaStatistiche();
                        break;
                    case 8:
                        archivio.stampaArchivioCompleto();
                        break;
                    case 9:
                        condizioneDiUscita = false;
                        System.out.println("-------- Applicazione terminata --------");
                        break;
                    default:
                        logger.error("Errore! Inserisci un numero valido");

                }
            } catch (ISBNNotFoundException e) {
                logger.error("Errore ricerca ISBN: " + e.getMessage());
            } catch (NumberFormatException e) {
                logger.error("Errore: Inserisci un valore numerico tra quelli proposti! " + e.getMessage());
            } catch (Exception e) {
                logger.error("Errore: " + e.getMessage());
            }
        }
    }

    //metodo per implementazione case1 - Aggiunta elemento
    public static void addElemento(Archivio archivio) throws Exception {
        System.out.println("Inserisci l' ISBN:");
        String isbn = scanner.nextLine();
        System.out.println("Inserisci titolo:");
        String titolo = scanner.nextLine();
        System.out.println("Inserisci l'anno di pubblicazione");
        int annoPubbl = Integer.parseInt(scanner.nextLine());


        System.out.println("Inserisci numero di pagine");
        int numPagine = Integer.parseInt(scanner.nextLine());
        System.out.println("Inserisci il tipo: 1- Libro, 2- Rivista");
        int selez = Integer.parseInt(scanner.nextLine());

        if (selez == 1) {
            System.out.println("Inserisci nome autore");
            String nomeAutore = scanner.nextLine();
            System.out.println("Inserisci il genere");
            String genere = scanner.nextLine();
            archivio.aggiungiElemento(new Libro(isbn, titolo, annoPubbl, numPagine, nomeAutore, genere));
        } else if (selez == 2) {

            System.out.println("Inserisci la periodicità tra le seguenti opzioni: ");
            System.out.println("1- SEMESTRALE, 2- SETTIMANALE, 3- MENSILE");

            int input = Integer.parseInt(scanner.nextLine());

            if (input == 1) {
                archivio.aggiungiElemento(new Rivista(isbn, titolo, annoPubbl, numPagine, Periodicita.SEMESTRALE));
                System.out.println("Elemento aggiunto con successo!");
            } else if (input == 2) {
                archivio.aggiungiElemento(new Rivista(isbn, titolo, annoPubbl, numPagine, Periodicita.SETTIMANALE));
                System.out.println("Elemento aggiunto con successo!");
            } else if (input == 3) {
                archivio.aggiungiElemento(new Rivista(isbn, titolo, annoPubbl, numPagine, Periodicita.MENSILE));
                System.out.println("Elemento aggiunto con successo!");
            } else {
                logger.error("Errore: Input non valido");
            }

        } else {
            logger.error("Tipo inserito non valido");
        }
    }

    //metodo per aggiornare un elemento esistente da isbn
    public static void aggiornaElemDaIsbn(String isbn) throws ISBNNotFoundException {
        ElementoCatalogo elementoDaModificare = archivio.catalogo.stream()
                .filter(ele -> ele.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new ISBNNotFoundException(" - Isbn non trovato"));

        if (elementoDaModificare instanceof Libro) {
            System.out.println("Cosa vuoi modificare di questo Libro?");
            System.out.println("1- Isbn, 2- Titolo, 3- Anno di pubblicazione, 4- numero di pagine");
            System.out.println("5- Nome Autore, 6- Genere");
            int selezioneLibro = Integer.parseInt(scanner.nextLine());
            switch (selezioneLibro) {
                case 1:
                    System.out.println("Inserisci il nuovo Isbn");
                    String isbnAggiornato = scanner.nextLine();
                    elementoDaModificare.setIsbn(isbnAggiornato);
                    System.out.println("Isbn modificato!");
                    break;
                case 2:
                    System.out.println("Inserisci nuovo titolo:");
                    String nuovoTitolo = scanner.nextLine();
                    elementoDaModificare.setTitolo(nuovoTitolo);
                    System.out.println("Titolo modificato!");
                    break;
                case 3:
                    System.out.println("Inserisci nuovo anno di pubblicazione:");
                    int nuovoAnno = Integer.parseInt(scanner.nextLine());
                    elementoDaModificare.setAnnoPubblicazione(nuovoAnno);
                    System.out.println("Anno modificato!");
                    break;
                case 4:
                    System.out.println("Inserisci il nuovo numero di pagine:");
                    int nuovoNumPag = Integer.parseInt(scanner.nextLine());
                    elementoDaModificare.setNumeroPagine(nuovoNumPag);
                    System.out.println("Numero pagine modificato!");
                    break;
                case 5:
                    System.out.println("Inserisci il nuovo nome dell'autore");
                    String nuovoAutore = scanner.nextLine();
                    ((Libro) elementoDaModificare).setAutore(nuovoAutore);
                    System.out.println("Autore modificato!");
                    break;
                case 6:
                    System.out.println("Inserisci il nuovo genere:");
                    String nuovoGenere = scanner.nextLine();
                    ((Libro) elementoDaModificare).setGenere(nuovoGenere);
                    break;
                default:
                    logger.error("Errore: Valore inserito non valido");
                    break;

            }
        } else if (elementoDaModificare instanceof Rivista) {
            System.out.println("Cosa vuoi modificare di questa Rivista?");
            System.out.println("1- Isbn, 2- Titolo, 3- Anno di pubblicazione, 4- numero di pagine, 5- Periodicità");
            int selezioneRivista = Integer.parseInt(scanner.nextLine());

            switch (selezioneRivista) {
                case 1:
                    System.out.println("Inserisci il nuovo Isbn");
                    String isbnAggiornato = scanner.nextLine();
                    elementoDaModificare.setIsbn(isbnAggiornato);
                    System.out.println("Isbn modificato!");
                    break;
                case 2:
                    System.out.println("Inserisci nuovo titolo:");
                    String nuovoTitolo = scanner.nextLine();
                    elementoDaModificare.setTitolo(nuovoTitolo);
                    System.out.println("Titolo modificato!");
                    break;
                case 3:
                    System.out.println("Inserisci nuovo anno di pubblicazione:");
                    int nuovoAnno = Integer.parseInt(scanner.nextLine());
                    elementoDaModificare.setAnnoPubblicazione(nuovoAnno);
                    System.out.println("Anno modificato!");
                    break;
                case 4:
                    System.out.println("Inserisci il nuovo numero di pagine:");
                    int nuovoNumPag = Integer.parseInt(scanner.nextLine());
                    elementoDaModificare.setNumeroPagine(nuovoNumPag);
                    System.out.println("Numero pagine modificato!");
                    break;
                case 5:
                    System.out.println("Inserisci la nuova periodicità tra le seguenti:");
                    System.out.println("1- SEMESTRALE, 2- SETTIMANALE, 3- MENSILE");
                    int nuovaPeriod = Integer.parseInt(scanner.nextLine());


                    if (nuovaPeriod == 1) {
                        ((Rivista) elementoDaModificare).setPeriodicita(Periodicita.SEMESTRALE);
                    } else if (nuovaPeriod == 2) {
                        ((Rivista) elementoDaModificare).setPeriodicita(Periodicita.SETTIMANALE);
                    } else if (nuovaPeriod == 3) {
                        ((Rivista) elementoDaModificare).setPeriodicita(Periodicita.MENSILE);
                    } else {
                        System.out.println("Errore: Input non valido");
                    }

                    System.out.println("Periodicità modificata!");
                    break;
                default:
                    logger.error("Errore: Valore inserito non valido");
                    break;

            }
        }
    }

    //metodo per generare un catalogo iniziale
    public static void inizializzaCatalogo() {
        ElementoCatalogo el1 = new Libro(faker.code().isbn10(true), faker.book().title(), faker.random().nextInt(1900, 2025), faker.random().nextInt(100, 500), faker.book().author(), faker.book().genre());
        ElementoCatalogo el2 = new Libro(faker.code().isbn10(true), faker.book().title(), faker.random().nextInt(1900, 2024), faker.random().nextInt(100, 500), faker.book().author(), faker.book().genre());
        ElementoCatalogo el3 = new Libro(faker.code().isbn10(true), faker.book().title(), faker.random().nextInt(1900, 2024), faker.random().nextInt(100, 500), faker.book().author(), faker.book().genre());
        ElementoCatalogo el4 = new Libro(faker.code().isbn10(true), faker.book().title(), faker.random().nextInt(1900, 2024), faker.random().nextInt(100, 500), faker.book().author(), faker.book().genre());
        ElementoCatalogo el5 = new Rivista(faker.code().isbn10(true), "Focus", faker.random().nextInt(1900, 2024), faker.random().nextInt(20, 200), Periodicita.SETTIMANALE);
        ElementoCatalogo el6 = new Rivista(faker.code().isbn10(true), "The Economist", faker.random().nextInt(1900, 2024), faker.random().nextInt(20, 200), Periodicita.MENSILE);
        ElementoCatalogo el7 = new Rivista(faker.code().isbn10(true), "Panorama", faker.random().nextInt(1900, 2024), faker.random().nextInt(20, 200), Periodicita.SEMESTRALE);
        catalogoIniziale.addAll(Arrays.asList(el1, el2, el3, el4, el5, el6, el7));
    }
}

