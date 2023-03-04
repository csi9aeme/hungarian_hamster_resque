import java.util.Scanner;

public class Controller {
    private static Scanner sc = new Scanner(System.in);
    private static Controller controller = new Controller();
    private static RunMenus run = new RunMenus();
    public static void main(String[] args) {

        int option = 0;
        while (option != 6) {

            controller.printMainMenu();

            option = sc.nextInt();
            sc.nextLine();

            run.selectMainMenuOption(option);
        }

    }

    private void printMainMenu() {
        System.out.println("1. Hörcsögök\n" +
                "2. Ideiglenes befogadók\n" +
                "3. Örökbefogadók\n" +
                "4. Riport\n" +
                "Választott menüpont: " );

    }

    private void printHamsterMenu() {
        System.out.println(
                "1. Új hörcsög regisztrálása\n" +
                "2. Hörcsög örökbeadása\n" +
                "3. Keresés név alapján\n" +
                "4. Keresés faj alapján\n" +
                "5. Keresés kor alapján\n" +
                "6. Keresés örökbefogadhatósági állapot alapján\n" +
                "7. Állapot átállítása\n" +
                "Választott menüpont: " );

    }
}
