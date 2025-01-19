package org.example.entities;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Archivio {
    public List<ElementoCatalogo> catalogo;

    public Archivio(List<ElementoCatalogo> catalogo) {
        this.catalogo = catalogo;
    }

    @Override
    public String toString() {
        return "Archivio{" +
                "catalogo=" + catalogo +
                '}';
    }

    //metodo per aggiungere elemento
    public void aggiungiElemento(ElementoCatalogo elemento) throws Exception {
        //se in catalogo trovi un qualunque match di un elemento che ha isbn uguale all'isbn dell'elemento inserito allora exception.
        //altrimenti aggiungo elemento a catalogo in archivio
        if (catalogo.stream().anyMatch(elementoCatalogo -> elementoCatalogo.getIsbn().equals(elemento.getIsbn()))) {
            throw new Exception("Un elemento con lo stesso ISBN è gia presente in archivio");
        } else {
            this.catalogo.add(elemento);
        }
    }

    //metodo di ricerca per isbn
    //cerca in catalogo, filtra tra gli elementi un elemento che isbn uguale a isbn richiesto e ritorna il primo elemento trovato.
    public void ricercaDaISBN(String isbn) throws ISBNNotFoundException {
        ElementoCatalogo risultato = catalogo.stream()
                .filter(ele -> ele.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new ISBNNotFoundException(" - Isbn non trovato!"));
        System.out.println(risultato);
    }

    //metodo rimozione elemento da isbn
    public void rimuoviElementoDaISBN(String isbn) {
        //dopo una ricerca dei metodi delle List ho trovato il metodo removeIf, perchè remove() non riuscivo a collegarlo a una condizione
        //https://www.w3schools.com/java/ref_arraylist_removeif.asp
        boolean rimosso = catalogo.removeIf(ele -> ele.getIsbn().equals(isbn));
        System.out.println(rimosso ? "Elemento con ISBN: " + isbn + " RIMOSSO CON SUCCESSO!" : "Elemento con ISBN: " + isbn + " NON TROVATO!");
    }

    //metodo ricerca per anno pubblicazione
    public void ricercaPerAnno(int anno) {
        //creo stream di catalogo, filtro tra gli elementi l'elemento con anno pubblicazione uguale ad anno inserito, e raccolgo in List.
        List<ElementoCatalogo> risultato = catalogo.stream()
                .filter(ele -> ele.getAnnoPubblicazione() == anno)
                .toList();

        if (!risultato.isEmpty()) {
            System.out.println(risultato);
        } else {
            System.out.println("Nessun elemento trovato con l'anno: " + anno);
        }
    }

    //metodo ricerca per autore
    public void ricercaPerAutore(String nomeAutore) {
        List<ElementoCatalogo> risultato = catalogo.stream()
                .filter(ele -> ele instanceof Libro && ((Libro) ele).getAutore().equals(nomeAutore))
                .toList();

        if (!risultato.isEmpty()) {
            System.out.println(risultato);
        } else {
            System.out.println("Nessun autore trovato con il nome: " + nomeAutore);
        }
    }

    //metodo aggiornamento di un elemento da isbn creato in Main

    //metodo stampa archivio
    public void stampaArchivioCompleto() {
        catalogo.forEach(System.out::println);
    }

    //metodo stampa statistiche catalogo
    public void stampaStatistiche() {
        //totali
        long totaleLibri = catalogo.stream().filter(ele -> ele instanceof Libro).count();
        long totaleRiviste = catalogo.stream().filter(ele -> ele instanceof Rivista).count();
        System.out.println("Il catalogo contiene un totale di:");
        System.out.println("Libri: " + totaleLibri);
        System.out.println("Riviste: " + totaleRiviste);
        //elemento più pagine
        ElementoCatalogo elementoConPiuPagine = catalogo.stream()
                .max(Comparator.comparing(ElementoCatalogo::getNumeroPagine)).get();
        System.out.println("Elemento con più pagine: " + elementoConPiuPagine);
        //media pagine di tutti gli elementi in catalogo
        OptionalDouble mediaPagineTot = catalogo.stream()
                .mapToDouble(ElementoCatalogo::getNumeroPagine)
                .average();
        System.out.println("La media delle pagine di tutti gli elementi in catalogo è: " + mediaPagineTot);

    }
}
