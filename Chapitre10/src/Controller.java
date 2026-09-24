import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import Exception.AuthorException;
import Exception.DateFormatException;
import Exception.DiskException;
import Exception.DuplicateException;

public class Controller {
    //TODO affichage du menu
    //ajout disque
    //suppression disque
    //affichage de la discothèque
    //Lecture du disque

    private static final Scanner scan = new Scanner(System.in);
    static void main() {
        while(true){
            menu();
        }
    }

    private static void menu(){
        String menu =
                """
                [1] Afficher la discothèque
                [2] Ajouter un Disque
                [3] Supprimer un Disque
                [4] Trier la liste
                [5] Quitter le programme
                """;

        System.out.println(menu);
        System.out.println("Quel est votre choix ?");

        int choice = scan.nextInt();
        scan.nextLine();

        switch (choice){
            case 1:
                GestionDisk.showLibDisk();
                break;
            case 2:
                addDiskMenu();
                break;
            case 3:
                deleteDiskMenu();
                break;
            case 4:
                GestionDisk.sortLibDiskByDate();
                break;
            case 5:
                System.exit(0);
            default:
                System.out.println("Une erreur est survenue, veuillez réesayer");
        }

    }

    private static void addDiskMenu(){
        String menu =
                """
                [1] Ajouter un disque avec le nom, l'auteur et l'année
                [2] Ajouter un disque avec le nom et l'auteur
                [3] Retour en arrière
                """;

        System.out.println(menu);
        System.out.println("Quel est votre choix ?");

        int choice = scan.nextInt();
        scan.nextLine();
        switch (choice){
            case 1:
                try{
                    addDisk(true);
                }catch (AuthorException | DateFormatException | DuplicateException | DiskException e){
                    System.out.println(e.getMessage());
                }

                break;
            case 2:
                try{
                    addDisk(false);
                }catch (AuthorException | DateFormatException | DuplicateException | DiskException e){
                    System.out.println(e.getMessage());
                }
                break;
        }
    }

    private static void deleteDiskMenu(){
        String menu =
                """
                [1] Supprimer un disque après sélection dans la discothèque
                [2] Supprimer un disque avec le nom et l'auteur
                [3] Supprimer un disque avec le nom
                [4] Retour en arrière
                """;

        System.out.println(menu);
        System.out.println("Quel est votre choix ?");

        int choice = scan.nextInt();
        scan.nextLine();

        switch (choice){
            case 1:
                try{
                    deleteDiskChoice();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Vous n'avez pas saisi un numéro de disque valide");
                }

                break;
            case 2:
                try{
                    deleteDisk(true);
                } catch (AuthorException | DuplicateException | DiskException e) {
                    System.out.println(e.getMessage());
                }

                break;
            case 3:
                try{
                    deleteDisk(false);
                } catch (AuthorException | DuplicateException | DiskException e) {
                    System.out.println(e.getMessage());
                }

                break;
        }
    }

    private static void addDisk(boolean withDate) throws AuthorException, DateFormatException, DuplicateException, DiskException {
        String diskName = promptDiskName();

        Author a = promptAuthor();
        if(withDate){
            LocalDate localDate = promptDate();
            GestionDisk.createDisk(diskName, a, localDate);
        }
        else{
            GestionDisk.createDisk(diskName, a);
        }
    }

    private static void deleteDiskChoice(){
        if(GestionDisk.getLibDisk().isEmpty()){
            System.out.println("La discothèque est vide");
            return;
        }
        GestionDisk.showLibDisk();
        System.out.println("Quel disque souhaitez vous supprimer ?");

        int indexToDelete = scan.nextInt() - 1;
        Disk diskToDelete = GestionDisk.getLibDisk().get(indexToDelete);
        GestionDisk.deleteDisk(diskToDelete);
    }

    private static void deleteDisk(boolean withAuthor) throws DuplicateException, AuthorException, DiskException {
        if(GestionDisk.getLibDisk().isEmpty()){
            System.out.println("La discothèque est vide");
            return;
        }

        String diskName = promptDiskName();

        if(withAuthor){
            Author a = promptAuthor();
            GestionDisk.deleteDisk(diskName, a);
        }
        else{
            GestionDisk.deleteDisk(diskName);
        }
    }

    private static Author promptAuthor() throws AuthorException {
        System.out.println("Saisissez le nom de l'auteur");
        String name = scan.nextLine();
        System.out.println("Saisissez le prénom de l'auteur");
        String firstName = scan.nextLine();

        if(name.isEmpty() || firstName.isEmpty()){
            throw new AuthorException("Nom ou prénom non saisi");
        }

        return new Author(name, firstName);
    }

    private static String promptDiskName() throws DiskException {
        System.out.println("Saisissez le nom du disque");
        String diskName = scan.nextLine();

        if(diskName.isEmpty()){
            throw new DiskException("Nom du disque non saisi");
        }

        return diskName;
    }

    private static LocalDate promptDate() throws DateFormatException {
        System.out.println("Saisissez la date de publication au format dd/mm/yyyy");
        String date = scan.nextLine();
        if(!date.matches("^\\d{2}/\\d{2}/\\d{4}$")){
            throw new DateFormatException("La date n'est pas au format dd/mm/yyyy");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(date, formatter);
    }
}
