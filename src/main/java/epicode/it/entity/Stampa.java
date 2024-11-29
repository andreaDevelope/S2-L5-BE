package epicode.it.entity;

public abstract class Stampa {
    private String isbn;
    private String titolo;
    private int annoPublicazione;
    private int numeroPagine;

    public int getAnnoPublicazione() {
        return annoPublicazione;
    }

    public void setAnnoPublicazione(int annoPublicazione) {
        this.annoPublicazione = annoPublicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public void setNumeroPagine(int numeroPagine) {
        this.numeroPagine = numeroPagine;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Stampa(int annoPublicazione, int numeroPagine, String titolo, String isbn) {
        this.annoPublicazione = annoPublicazione;
        this.numeroPagine = numeroPagine;
        this.titolo = titolo;
        this.isbn = isbn;
    }



}
