package org.example.entities;

import java.util.List;
import java.util.Optional;

public class Archivio {
    private List<ElementoCatalogo> catalogo;

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
        //altrimenti aggiungi elemento a catalogo in archivio
        if (catalogo.stream().anyMatch(elementoCatalogo -> elementoCatalogo.getIsbn().equals(elemento.getIsbn()))) {
            throw new Exception("Un elemento con lo stesso ISBN è gia presente in archivio");
        } else {this.catalogo.add(elemento);}

    }

    //metodo di ricerca per isbn
    public Optional<ElementoCatalogo> ricercaDaISBN (String isbn) {
        //cerca in catalogo, filtra tra gli elementi un elemento che isbn uguale a isbn richiesto e ritorna il primo elemento trovato.
        return catalogo.stream()
                .filter(ele -> ele.getIsbn().equals(isbn))
                .findFirst();
    }

    //metodo rimozione elemento da isbn

    public boolean rimuoviElementoDaISBN(String isbn) {
        //dopo una ricerca dei metodi delle List ho trovato il metodo removeIf, perchè remove() non riuscivo a collegarlo a una condizione
        //https://www.w3schools.com/java/ref_arraylist_removeif.asp
        return catalogo.removeIf(ele -> ele.getIsbn().equals(isbn));

    }

    //metodo ricerca per anno pubblicazione
    public List<ElementoCatalogo> ricercaPerAnno (int anno) {
        //creo stream di catalogo, filtro tra gli elementi l'elemento con anno pubblicazione uguale ad anno inserito, e raccolgo in List.
        return catalogo.stream()
                .filter(ele -> ele.getAnnoPubblicazione() == anno)
                .toList();
    }

    //metodo ricerca per autore
   /* public void ricercaPerAutore (String nomeAutore) {
       return catalogo.stream()
               .filter(ele -> ele instanceof Libro)
    }*/

    //aggiornamento di un elemento da isbn

    //statistiche catalogo
}
