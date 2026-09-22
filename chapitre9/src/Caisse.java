import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Caisse {

    static Scanner scanner = new Scanner(System.in);

    public record Produit(String nom, float prix, int quantite) {
        @Override
        public String toString() {
            return String.format("%s %.2f€/u x%d total:%.2f€", nom, prix, quantite, prix * quantite);
        }

        public float total() {
            return prix*quantite;
        }
    }

    static void main() {
        ArrayList<Produit> list = new ArrayList<>();
        while (true){
            menu(list, scanner);
        }
    }

    public static void menu(ArrayList<Produit> array, Scanner scanner){
        int choice = 0;

        System.out.println("[1] Voir mon ticket");
        System.out.println("[2] Ajouter des produits");
        System.out.println("[3] Supprimer des produits");
        System.out.println("[4] Vider le panier");
        System.out.println("[5] Quitter \n");

        boolean exception;

        do{
            try {
                System.out.println("Veuillez choisir l'opération à faire: ");
                choice = scanner.nextInt();
                exception = false;
            }catch (InputMismatchException e){
                System.out.println("Une erreur est survenue, veuillez réessayer");
                exception = true;
                scanner.nextLine();
            }
        } while (exception);



        switch (choice){
            case 1:
                afficherTicket(array);
                break;
            case 2:
                try{
                    ajouterArticles(array);
                } catch (FullListException e) {
                    System.out.println(e.getMessage());
                }

                break;
            case 3:
                supprimerArticle(array);
                break;
            case 4:
                viderPanier(array);
                break;
            case 5:
                System.exit(0);
            default:
                System.out.println("Une erreur est survenue, veuillez réesayer");
        }
    }
    private static void ajouterArticles(ArrayList<Produit> array) throws FullListException {
        boolean exception;
        Produit produit;

        int nbProduit = 0;
        do{
            System.out.println("Combien de produit voulez-vous ajouter ?");
            try {
                nbProduit = scanner.nextInt();
                exception = false;
            }catch (InputMismatchException e){
                System.out.println("Une erreur est survenue, veuillez réessayer");
                exception = true;
                scanner.nextLine();
            }

        }while (exception || nbProduit <= 0);

        String nom = "";
        float prix = 0;
        int quantite = 0;

        for (int i = 0; i < nbProduit; i++){

            System.out.println("Nom du produit");

            scanner.nextLine();
            nom = scanner.nextLine();
            do{
                try {
                    System.out.println("Prix du produit");
                    prix = scanner.nextFloat();
                    exception = false;
                } catch (InputMismatchException e) {
                    System.out.println("Une erreur est survenue, veuillez réessayer");
                    exception = true;
                    scanner.nextLine();
                }
            } while (exception || prix <= 0);


            do{
                try {
                    System.out.println("Quantité");
                    quantite = scanner.nextInt();
                    exception = false;
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Une erreur est survenue, veuillez réessayer");
                    exception = true;
                    scanner.nextLine();
                }
            } while (exception || quantite <= 0);


            produit = new Produit(nom, prix, quantite);
            array.add(produit);
            if(array.size() > 5){
                throw new FullListException();
            }
        }
    }

    private static void supprimerArticle(ArrayList<Produit> array){
        if (array.isEmpty()){
            System.out.println("Le panier est vide, rien à supprimer");
            return;
        }
        afficherList(array);
        System.out.println("Quel produit voulez-vous supprimer ?");

        int indexToRemove = -1;
        boolean exception;
        do{
            exception = false;
            try {
                indexToRemove = scanner.nextInt() - 1;
            }catch (InputMismatchException e){
                System.out.println("Une erreur est survenue, veuillez réessayer");
                exception = true;
                scanner.nextLine();
            }

        }while (exception || indexToRemove < 0 || indexToRemove > array.size());

        Produit produitToRemove = array.get(indexToRemove);
        array.remove(indexToRemove);

        System.out.println("Le Produit " + produitToRemove + " a bien été supprimé");
    }

    private static void viderPanier(ArrayList<Produit> array){
        System.out.println("Nettoyage du panier");
        array.clear();
        System.out.println("Panier vidé");
    }

    private static float totalPanier(ArrayList<Produit> array){
        float total = 0;
        for (Produit produit : array) {
            total += produit.total();
        }

        return total;
    }

    private static void afficherList(ArrayList<Produit> array){
        for (int i = 0; i < array.size(); i++){
            System.out.println("["+ (i+1) + "] " + array.get(i));
        }
    }

    private static void afficherTicket(ArrayList<Produit> array){
        for (Produit produit : array) {
            System.out.println(produit);
        }
        System.out.printf("Total du panier : %.2f€%n", totalPanier(array));
    }
}
