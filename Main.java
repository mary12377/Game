import javax.management.modelmbean.ModelMBeanAttributeInfo;
import java.util.Scanner;

public class Main   {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String secretNumber;

        System.out.print("Введите цифры");
        secretNumber = scanner.nextLine();

        Game game = new Game(secretNumber);
        game.play();
    }
}