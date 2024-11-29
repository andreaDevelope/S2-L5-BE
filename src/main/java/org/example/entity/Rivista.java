package org.example.entity;

public class Rivista extends Stampa {
    private Periodicita periodicita;

    public enum Periodicita {
        SETTIMANALE, MENSILE, SEMESTRALE
    }

    public Rivista(int annoPublicazione, int numeroPagine, String titolo, String isbn, Periodicita periodicita) {
        super(annoPublicazione, numeroPagine, titolo, isbn);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "isbn='" + getIsbn() + '\'' +
                ", titolo='" + getTitolo() + '\'' +
                ", annoPubblicazione=" + getAnnoPublicazione() +
                ", numeroPagine=" + getNumeroPagine() +
                ", periodicita=" + periodicita +
                '}';
    }
}
