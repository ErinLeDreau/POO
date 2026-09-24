import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Disk {

    private String name;
    private Author author;
    private LocalDate date;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Disk(String name, Author author, LocalDate date) {
        this.author = author;
        this.name = name;
        this.date = date;
    }

    public Disk(String name, Author author) {
        this.author = author;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disk disk = (Disk) o;

        return Objects.equals(name, disk.getName()) && Objects.equals(author, disk.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author);
    }

    @Override
    public String toString() {
        String dateFormatted = (date != null) ? date.format(DATE_FORMATTER) : "Année inconnue";

        return
                "nom='" + name + '\'' +
                ", auteur=" + author +
                ", date=" + dateFormatted;
    }
}
