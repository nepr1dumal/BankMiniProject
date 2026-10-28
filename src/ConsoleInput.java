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

}
