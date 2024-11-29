package org.example;

import org.example.entity.Libro;
import org.example.entity.Rivista;
import org.example.entity.Stampa;
import org.example.exceptions.DuplicatedIsbnException;
import org.example.exceptions.StampaNonTrovataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Archivio {
    public static void main(String[] args)  {
        Libro libro1 = new Libro(2024, 600, "libro bellissimo1", "0000-0000-0000", "autore bellissimo", "thriller");
        Libro libro2 = new Libro(2024, 600, "libro bellissimo2", "1000-0000-0000", "autore bellissimo", "thriller");
        Libro libro3 = new Libro(2024, 600, "libro bellissimo3", "2000-0000-0000", "autore bellissimo2", "thriller");
        Libro libro4 = new Libro(2024, 600, "libro bellissimo4", "3000-0000-0000", "autore bellissimo", "thriller");
        Libro libro5 = new Libro(2024, 600, "libro bellissimo5", "4000-0000-0000", "autore bellissimo2", "thriller");

        Rivista rivista1 = new Rivista(2024, 600, "rivista bellissima1", "5000-0000-0000", Rivista.Periodicita.MENSILE);
        Rivista rivista2 = new Rivista(2024, 600, "rivista bellissima2", "6000-0000-0000", Rivista.Periodicita.SEMESTRALE);
        Rivista rivista3 = new Rivista(2024, 600, "rivista bellissima3", "7000-0000-0000", Rivista.Periodicita.SETTIMANALE);
        Rivista rivista4 = new Rivista(2024, 600, "rivista bellissima4", "8000-0000-0000", Rivista.Periodicita.SEMESTRALE);
        Rivista rivista5 = new Rivista(2024, 600, "rivista bellissima5", "9000-0000-0000", Rivista.Periodicita.MENSILE);

        List<Stampa> archivio = new ArrayList<>();
        archivio.addAll(Arrays.asList(libro1, libro2, libro3, libro4, rivista1, rivista2, rivista3, rivista4, rivista5));

        System.out.println("\n-------------addStampa-----------------\n");

        try {
            addStampa(rivista5, archivio);
        } catch (DuplicatedIsbnException e) {
            System.out.println(e.getMessage());
        }


        Libro libro6 = new Libro(2025, 300, "libro nuovo", "1001-0000-0000", "autore interessante", "fantasy");
        try {
            addStampa(libro6, archivio);
        } catch (DuplicatedIsbnException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n-------------findByIsbn-----------------\n");

        try {
            Stampa trovata = findByIsbn("5000-0000-0000", archivio);
            System.out.println("Stampa trovata: " + trovata);
        } catch (StampaNonTrovataException e) {
            System.out.println(e.getMessage());
        }

        try {
            Stampa trovata = findByIsbn("9999-9999-9999", archivio);
            System.out.println("Stampa trovata: " + trovata);
        } catch (StampaNonTrovataException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n-------------removeByIsbn-----------------\n");


        try {
            removeByIsbn("0000-0000-0000", archivio);
        } catch (StampaNonTrovataException e) {
            System.out.println("Errore: " + e.getMessage());
        }

        try {
            removeByIsbn("9999-9999-9999", archivio);
        } catch (StampaNonTrovataException e) {
            System.out.println("Errore: " + e.getMessage());
        }

        System.out.println("\n-------------findByAnno-----------------\n");


        int annoRicerca = 2024;
        List<Stampa> risultati = findByAnno(annoRicerca, archivio);

        System.out.println("Stampe trovate per l'anno " + annoRicerca + ":");
        for (Stampa stampa : risultati) {
            System.out.println(stampa);
        }

        // in caso di nessun risultato lista vuota
        annoRicerca = 2025;
        risultati = findByAnno(annoRicerca, archivio);

        System.out.println("Stampe trovate per l'anno " + annoRicerca + ":");
        for (Stampa stampa : risultati) {
            System.out.println(stampa);
        }

        System.out.println("\n-------------findByAutore-----------------\n");

        //in caso non trova l Autore restituisce una lista vuota
        String autoreRicerca = "autore bellissimo";
        List<Libro> risultati2 = findByAutore(autoreRicerca, archivio);

        System.out.println("Libri trovati per l'autore \"" + autoreRicerca + "\":");
        for (Libro libro : risultati2) {
            System.out.println(libro);
        }

        System.out.println("\n-------------updateStampa-----------------\n");

        // Aggiornamento di un elemento esistente
        try {
            Libro libroAggiornato = new Libro(2024, 700, "libro bellissimo aggiornato", "0000-0000-0000", "autore aggiornato", "fantasy");
            updateStampa("1000-0000-0000", libroAggiornato, archivio);
        } catch (StampaNonTrovataException e) {
            System.out.println("Errore: " + e.getMessage());
        }

        // Prova ad aggiornare un elemento non esistente
        try {
            Rivista rivistaAggiornata = new Rivista(2025, 300, "rivista inesistente aggiornata", "9999-9999-9999", Rivista.Periodicita.SEMESTRALE);
            updateStampa("9999-9999-9999", rivistaAggiornata, archivio);
        } catch (StampaNonTrovataException e) {
            System.out.println("Errore: " + e.getMessage());
        }

    }


    public static void addStampa(Stampa param, List<Stampa> archivio) {
        for (Stampa stampa : archivio) {
            if (param.getIsbn().equals(stampa.getIsbn())) {
                throw new DuplicatedIsbnException("Codice ISBN già presente in archivio: " + param.getIsbn());
            }
        }
        archivio.add(param);
        System.out.println(param + " è stato aggiunto all'archivio");
    }

    public static Stampa findByIsbn(String isbn, List<Stampa> archivio) {
        for (Stampa stampa : archivio) {
            if (stampa.getIsbn().equals(isbn)) {
                return stampa;
            }
        }
        throw new StampaNonTrovataException("Nessuna stampa trovata con ISBN: " + isbn);
    }

    public static void removeByIsbn(String isbn, List<Stampa> archivio) {
        Stampa stampaDaRimuovere = null;
        for (Stampa stampa : archivio) {
            if (stampa.getIsbn().equals(isbn)) {
                stampaDaRimuovere = stampa;
                break;
            }
        }

        if (stampaDaRimuovere == null) {
            throw new StampaNonTrovataException("Nessuna stampa trovata con ISBN: " + isbn);
        }

        archivio.remove(stampaDaRimuovere);
        System.out.println("Stampa rimossa con successo: " + stampaDaRimuovere);
    }

    public static List<Stampa> findByAnno(int anno, List<Stampa> archivio) {
        List<Stampa> risultati = new ArrayList<>();
        for (Stampa stampa : archivio) {
            if (stampa.getAnnoPublicazione() == anno) {
                risultati.add(stampa);
            }
        }
        return risultati;
    }


    public static List<Libro> findByAutore(String autore, List<Stampa> archivio) {
        List<Libro> risultati = new ArrayList<>();
        for (Stampa stampa : archivio) {
            if (stampa instanceof Libro) { // Controlla se è un Libro
                Libro libro = (Libro) stampa;
                if (libro.getAutore().equalsIgnoreCase(autore)) { // Confronta ignorando maiuscole/minuscole
                    risultati.add(libro);
                }
            }
        }
        return risultati;
    }

    public static void updateStampa(String isbn, Stampa nuovaStampa, List<Stampa> archivio) {
        for (int i = 0; i < archivio.size(); i++) {
            Stampa stampa = archivio.get(i);
            if (stampa.getIsbn().equals(isbn)) {
                archivio.set(i, nuovaStampa); // Sostituisce l'elemento con quello aggiornato
                System.out.println("Stampa aggiornata con successo: " + nuovaStampa);
                return;
            }
        }
        throw new StampaNonTrovataException("Nessuna stampa trovata con ISBN: " + isbn);
    }





}
