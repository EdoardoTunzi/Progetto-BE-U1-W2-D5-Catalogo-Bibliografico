package org.example;

import org.example.entities.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class Main {

    static List<ElementoCatalogo> catalogoIniziale = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static Archivio archivio = new Archivio(catalogoIniziale);

    public static void main(String[] args) throws Exception {

        inizializzaCatalogo();

        //--------TEST--------
        /*System.out.println(archivio);
        System.out.println(archivio.ricercaDaISBN("isbn12"));
        System.out.println("elementi del 1984: " + archivio.ricercaPerAnno(1984));
        archivio.rimuoviElementoDaISBN("isbn2");
        System.out.println(archivio.ricercaPerAutore("Tolkien"));
        System.out.println(archivio);*/

        boolean condizioneDiUscita = true;

        while (condizioneDiUscita) {
            System.out.println("Che operazione vuoi eseguire? Digita il numero corrispondente.");
            System.out.println("1- Aggiungi elemento a catalogo");
            System.out.println("2- Cerca elemento per ISBN");
            System.out.println("3- Cerca elemento per Anno di pubblicazione");
            System.out.println("4- Cerca per Autore");
            System.out.println("5- Rimuovi da ISBN");
            System.out.println("6- Termina");
            int selezione = Integer.parseInt(scanner.nextLine());

            switch (selezione) {
                case 1:
                    try {
                        addElemento(archivio);
                        System.out.println("Elemento aggiunto con successo!");
                    } catch (Exception e) {
                        System.out.println("Errore in aggiunta elemento" + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Inserisci l'ISBN da cercare:");
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
                case 6:
                    condizioneDiUscita = false;
                    System.out.println("--------Applicazione terminata------");
                    break;
                default:
                    System.out.println("Errore! Inserisci un numero valido");

            }
        }
    }
    //metodo per gestire case1 - Aggiunta elemento
    public static void addElemento (Archivio archivio) throws Exception {
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
            System.out.println("1-SEMESTRALE, 2- SETTIMANALE, 3- MENSILE");

            int input = Integer.parseInt(scanner.nextLine());

                if (input == 1) {
                    archivio.aggiungiElemento(new Rivista(isbn, titolo, annoPubbl, numPagine, Periodicita.SEMESTRALE));
                } else if (input == 2) {
                    archivio.aggiungiElemento(new Rivista(isbn, titolo, annoPubbl, numPagine, Periodicita.SETTIMANALE));
                } else if (input == 3) {
                    archivio.aggiungiElemento(new Rivista(isbn, titolo, annoPubbl, numPagine, Periodicita.MENSILE));
                } else {
                    System.out.println("Errore: Input non valido");
                }

        } else {
            System.out.println("Tipo inserito non valido");
        }
    }

    public static void inizializzaCatalogo() {
        ElementoCatalogo el1 = new Libro("isbn1", "The Alchemist", 1984, 150, "Paulo Coelho", "romanzo");
        ElementoCatalogo el2 = new Libro("isbn12", "The Power of Now", 2001, 130, "Eckart Tolle", "spiritualità");
        ElementoCatalogo el3 = new Libro("isbn123", "Lo Hobbit", 1974, 230, "Tolkien", "romanzo");
        ElementoCatalogo el4 = new Libro("isbn124", "1984", 1984, 150, "Tolkien", "romanzo");
        ElementoCatalogo el5 = new Rivista("isbn211", "Focus", 2025, 50, Periodicita.SETTIMANALE);
        ElementoCatalogo el6 = new Rivista("isbn212", "The Economist", 2025, 30, Periodicita.MENSILE);
        ElementoCatalogo el7 = new Rivista("isbn213", "Panorama", 2025, 20, Periodicita.SEMESTRALE);
        catalogoIniziale.addAll(Arrays.asList(el1, el2, el3, el4, el5, el6, el7));
    }
}
