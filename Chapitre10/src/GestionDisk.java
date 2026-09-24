import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import Exception.DuplicateException;
public class GestionDisk {

    private static ArrayList<Disk> libDisk = new ArrayList<>();

    public static void createDisk(String n, Author a, LocalDate d) throws DuplicateException {
        Disk disk = new Disk(n, a, d);
        createDisk(disk);
    }

    public static void createDisk(String n, Author a) throws DuplicateException {
        Disk disk = new Disk(n, a);
        createDisk(disk);
    }

    public static void createDisk(Disk disk) throws DuplicateException {
        if (!libDisk.contains(disk)){
            libDisk.add(disk);
        }
        else{
            throw new DuplicateException("Un disque de même nom et auteur existe déjà");
        }
    }

    public static void deleteDisk(Disk disk){
        if (!libDisk.remove(disk)){
            System.out.println("Ce disque n'est pas présent ou n'a pas pu être supprimer");
            return;
        }

        System.out.println("Le disque " + disk + " a bien été supprimer de la discothèque");
    }

    public static void deleteDisk(String n, Author a){
        Disk diskToDelete = new Disk(n,a);
        deleteDisk(diskToDelete);
    }

    public static void deleteDisk(String n) throws DuplicateException {
        Disk diskToFind = null;

        for (Disk disk : libDisk) {
            if (disk.getName().equals(n)) {
                if (diskToFind != null) {
                    throw new DuplicateException("Plusieurs disques existent avec le nom : " + n);
                }
                diskToFind = disk;
            }
        }

        if (diskToFind != null) {
            deleteDisk(diskToFind);
        }
    }

    public static void showLibDisk(){
        if (libDisk.isEmpty()) {
            System.out.println("La discothèque est vide.");
            return;
        }

        System.out.println("--- Liste des disques ---");
        for (int i = 0; i < libDisk.size(); i++) {
            int indexHumain = i + 1;
            Disk disk = libDisk.get(i);

            System.out.println(indexHumain + ". " + disk);
        }
    }

    public static void sortLibDiskByDate(){
        libDisk.sort(
                    Comparator.comparing(Disk::getDate, Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparing(Disk::getName, String.CASE_INSENSITIVE_ORDER)
                );
    }

    public static ArrayList<Disk> getLibDisk() {
        return libDisk;
    }

    public static void setLibDisk(ArrayList<Disk> libDisk) {
        GestionDisk.libDisk = libDisk;
    }
}
