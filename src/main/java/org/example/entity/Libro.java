package org.example.entity;

public class Libro extends Stampa{
    private String autore;
    private String genere;


    public Libro(int annoPublicazione, int numeroPagine, String titolo, String isbn, String autore, String genere) {
        super(annoPublicazione, numeroPagine, titolo, isbn);
        this.autore = autore;
        this.genere = genere;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + getIsbn() + '\'' +
                ", titolo='" + getTitolo() + '\'' +
                ", annoPubblicazione=" + getAnnoPublicazione() +
                ", numeroPagine=" + getNumeroPagine() +
                ", autore=" + getAutore() +
                "genere=" + getGenere() +
                '}';
    }
}
