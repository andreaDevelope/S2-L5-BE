package epicode.it;

import epicode.it.entity.Libro;
import epicode.it.entity.Rivista;
import epicode.it.entity.Stampa;
import epicode.it.exception.DuplicatedIsbnException;
import epicode.it.exception.StampaNonTrovataException;

import java.util.*;

public class Archivio {

    public static void main(String[] args) {
        List<Stampa> archivio = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        boolean continua = true;
        while (continua) {
            System.out.println("\nScegli un'opzione:");
            System.out.println("1. Aggiungi stampa");
            System.out.println("2. Trova per ISBN");
            System.out.println("3. Rimuovi per ISBN");
            System.out.println("4. Trova per anno");
            System.out.println("5. Trova per autore");
            System.out.println("6. Aggiorna stampa");
            System.out.println("7. Statistiche del catalogo");
            System.out.println("0. Esci");

            try {
                int scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1:
                        aggiungiStampa(scanner, archivio);
                        break;
                    case 2:
                        trovaStampaPerIsbn(scanner, archivio);
                        break;
                    case 3:
                        rimuoviStampaPerIsbn(scanner, archivio);
                        break;
                    case 4:
                        trovaStampePerAnno(scanner, archivio);
                        break;
                    case 5:
                        trovaLibriPerAutore(scanner, archivio);
                        break;
                    case 6:
                        aggiornaStampa(scanner, archivio);
                        break;
                    case 7:
                        visualizzaStatisticheCatalogo(archivio);
                        break;
                    case 0:
                        continua = false;
                        System.out.println("Chiusura applicazione...");
                        break;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Errore: inserisci un valore numerico valido.");
                scanner.nextLine();
            } catch (DuplicatedIsbnException | StampaNonTrovataException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void aggiungiStampa(Scanner scanner, List<Stampa> archivio) {
        try {
            Stampa nuovaStampa = creaStampa(scanner);
            if (archivio.stream().anyMatch(stampa -> stampa.getIsbn().equals(nuovaStampa.getIsbn()))) {
                throw new DuplicatedIsbnException("ISBN già presente in archivio: " + nuovaStampa.getIsbn());
            }
            archivio.add(nuovaStampa);
            System.out.println("Stampa aggiunta con successo: " + nuovaStampa);
        } catch (IllegalArgumentException e) {
            System.out.println("Errore nella creazione della stampa: " + e.getMessage());
        }
    }

    private static void trovaStampaPerIsbn(Scanner scanner, List<Stampa> archivio) {
        System.out.print("Inserisci l'ISBN: ");
        String isbn = scanner.nextLine();
        try {
            Stampa stampa = archivio.stream()
                    .filter(item -> item.getIsbn().equals(isbn))
                    .findFirst()
                    .orElseThrow(() -> new StampaNonTrovataException("Nessuna stampa trovata con ISBN: " + isbn));
            System.out.println("Stampa trovata: " + stampa);
        } catch (StampaNonTrovataException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rimuoviStampaPerIsbn(Scanner scanner, List<Stampa> archivio) {
        System.out.print("Inserisci l'ISBN della stampa da rimuovere: ");
        String isbn = scanner.nextLine();
        try {
            Stampa stampaDaRimuovere = archivio.stream()
                    .filter(item -> item.getIsbn().equals(isbn))
                    .findFirst()
                    .orElseThrow(() -> new StampaNonTrovataException("Nessuna stampa trovata con ISBN: " + isbn));
            archivio.remove(stampaDaRimuovere);
            System.out.println("Stampa rimossa con successo: " + stampaDaRimuovere);
        } catch (StampaNonTrovataException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void trovaStampePerAnno(Scanner scanner, List<Stampa> archivio) {
        System.out.print("Inserisci l'anno di pubblicazione: ");
        int anno = scanner.nextInt();
        scanner.nextLine();
        List<Stampa> stampe = archivio.stream()
                .filter(item -> item.getAnnoPublicazione() == anno)
                .toList();
        if (stampe.isEmpty()) {
            System.out.println("Nessuna stampa trovata per l'anno: " + anno);
        } else {
            System.out.println("Stampe trovate:");
            stampe.forEach(System.out::println);
        }
    }

    private static void trovaLibriPerAutore(Scanner scanner, List<Stampa> archivio) {
        System.out.print("Inserisci l'autore: ");
        String autore = scanner.nextLine();
        List<Libro> libri = archivio.stream()
                .filter(item -> item instanceof Libro)
                .map(item -> (Libro) item)
                .filter(libro -> libro.getAutore().equalsIgnoreCase(autore))
                .toList();
        if (libri.isEmpty()) {
            System.out.println("Nessun libro trovato per l'autore: " + autore);
        } else {
            System.out.println("Libri trovati:");
            libri.forEach(System.out::println);
        }
    }

    private static void aggiornaStampa(Scanner scanner, List<Stampa> archivio) throws StampaNonTrovataException, IllegalArgumentException {
        System.out.print("Inserisci l'ISBN della stampa da aggiornare: ");
        String isbn = scanner.nextLine();

        Stampa stampaEsistente = archivio.stream()
                .filter(item -> item.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new StampaNonTrovataException("Nessuna stampa trovata con ISBN: " + isbn));

        System.out.println("Stampa trovata: " + stampaEsistente);
        System.out.println("Aggiorna i campi (lascia vuoto per mantenere il valore attuale):");

        System.out.print("Inserisci nuovo titolo: ");
        String nuovoTitolo = scanner.nextLine();
        if (!nuovoTitolo.isEmpty()) {
            stampaEsistente.setTitolo(nuovoTitolo);
        }

        System.out.print("Inserisci nuovo anno di pubblicazione: ");
        String nuovoAnno = scanner.nextLine();
        if (!nuovoAnno.isEmpty()) {
            stampaEsistente.setAnnoPublicazione(Integer.parseInt(nuovoAnno));
        }

        System.out.print("Inserisci nuovo numero di pagine: ");
        String nuovoNumeroPagine = scanner.nextLine();
        if (!nuovoNumeroPagine.isEmpty()) {
            stampaEsistente.setNumeroPagine(Integer.parseInt(nuovoNumeroPagine));
        }

        if (stampaEsistente instanceof Libro) {
            Libro libro = (Libro) stampaEsistente;

            System.out.print("Inserisci nuovo autore: ");
            String nuovoAutore = scanner.nextLine();
            if (!nuovoAutore.isEmpty()) {
                libro.setAutore(nuovoAutore);
            }

            System.out.print("Inserisci nuovo genere: ");
            String nuovoGenere = scanner.nextLine();
            if (!nuovoGenere.isEmpty()) {
                libro.setGenere(nuovoGenere);
            }
        } else if (stampaEsistente instanceof Rivista) {
            Rivista rivista = (Rivista) stampaEsistente;

            System.out.print("Inserisci nuova periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
            String nuovaPeriodicita = scanner.nextLine();
            if (!nuovaPeriodicita.isEmpty()) {
                rivista.setPeriodicita(Rivista.Periodicita.valueOf(nuovaPeriodicita.toUpperCase()));
            }
        }

        System.out.println("Stampa aggiornata con successo: " + stampaEsistente);
    }

    private static void visualizzaStatisticheCatalogo(List<Stampa> archivio) {
        long numeroLibri = archivio.stream().filter(item -> item instanceof Libro).count();
        long numeroRiviste = archivio.stream().filter(item -> item instanceof Rivista).count();
        double mediaPagine = archivio.stream().mapToInt(Stampa::getNumeroPagine).average().orElse(0);
        Stampa stampaMaxPagine = archivio.stream()
                .max(Comparator.comparingInt(Stampa::getNumeroPagine))
                .orElse(null);

        System.out.println("Statistiche del catalogo:");
        System.out.println("Numero di libri: " + numeroLibri);
        System.out.println("Numero di riviste: " + numeroRiviste);
        System.out.println("Media delle pagine: " + mediaPagine);
        if (stampaMaxPagine != null) {
            System.out.println("Stampa con il maggior numero di pagine: " + stampaMaxPagine);
        } else {
            System.out.println("Nessuna stampa disponibile.");
        }
    }

    private static Stampa creaStampa(Scanner scanner) {
        System.out.print("Inserisci il tipo di stampa (1: Libro, 2: Rivista): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Inserisci ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Inserisci titolo: ");
        String titolo = scanner.nextLine();

        System.out.print("Inserisci anno di pubblicazione: ");
        int anno = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Inserisci numero di pagine: ");
        int numeroPagine = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Inserisci autore: ");
            String autore = scanner.nextLine();
            System.out.print("Inserisci genere: ");
            String genere = scanner.nextLine();
            return new Libro(anno, numeroPagine, titolo, isbn, autore, genere);
        } else if (tipo == 2) {
            System.out.print("Inserisci periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
            String periodicita = scanner.nextLine();
            return new Rivista(anno, numeroPagine, titolo, isbn, Rivista.Periodicita.valueOf(periodicita.toUpperCase()));
        } else {
            throw new IllegalArgumentException("Tipo di stampa non valido.");
        }
    }
}