import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class ConsoleInput {

    public static BigDecimal readAmount(Scanner scan) {
        while(true) {
            System.out.print("Сумма (отмена - Enter): ");
            try {
                String input = scan.nextLine().trim();
                if (input.isEmpty()) {
                    return null;
                } else {
                    BigDecimal amount = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
                    if (amount.signum() > 0) {
                        return amount;
                    }
                }
                System.out.println("Сумма должна быть больше 0");
            } catch (NumberFormatException e) {
                System.out.println("Нужно ввести число");
            }
        }
    }

    public static String readCard(Scanner scan) {
        while (true) {
            System.out.print("Введите номер карты (отмена - Enter): ");
                String card = scan.nextLine().trim().replace(" ", "");
                if (card.isEmpty()) {
                    return null;
                } else if (card.matches("\\d{16}")) {
                    return card;
                } else {
                    System.out.println("Неверный формат");
            }
        }
    }

    public static boolean readYesNo(Scanner scan) {
        while (true) {
            System.out.print("Y/N: ");
            String choose = scan.nextLine().trim().toUpperCase();
            if (choose.isEmpty() || choose.equals("N")) {
                return false;
            } else if (choose.equals("Y")) {
                return true;
            } else {
                System.out.println("Неверный ввод");
            }
        }
    }

    public static String readName(Scanner scan, String prompt) {
        while (true) {
            System.out.print(prompt);
            String name = scan.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Поле не может быть пустым");
            } else {
                return name;
            }
        }
    }

}
