import java.util.ArrayList;
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
        int choice;

        System.out.println("[1] Voir mon ticket");
        System.out.println("[2] Ajouter des produits");
        System.out.println("[3] Supprimer des produits");
        System.out.println("[4] Vider le panier");
        System.out.println("[5] Quitter \n");

        System.out.println("Veuillez choisir l'opération à faire: ");
        choice = scanner.nextInt();

        switch (choice){
            case 1:
                afficherTicket(array);
                break;
            case 2:
                ajouterArticles(array);
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
    private static void ajouterArticles(ArrayList<Produit> array){
        String nom;
        float prix;
        int quantite;
        Produit produit;

        System.out.println("Combien de produit voulez-vous ajouter ?");
        int nbProduit;
        do{
            nbProduit = scanner.nextInt();
        }while (nbProduit < 0);

        for (int i = 0; i < nbProduit; i++){
            System.out.println("Nom du produit");
            nom = scanner.nextLine();

            System.out.println("Prix du produit");
            prix = scanner.nextFloat();

            System.out.println("Quantité");
            quantite = scanner.nextInt();

            produit = new Produit(nom, prix, quantite);
            array.add(produit);
        }
    }

    private static void supprimerArticle(ArrayList<Produit> array){
        afficherList(array);
        System.out.println("Quel produit voulez-vous supprimer ?");

        int indexToRemove;
        do{
            indexToRemove = scanner.nextInt() - 1;
        }while (indexToRemove < 0 || indexToRemove > array.size());

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
