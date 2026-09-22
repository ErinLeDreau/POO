public class FullListException extends Exception{

    @Override
    public String getMessage() {
        return "Le panier ne peut pas avoir plus de 5 articles";
    }
}
