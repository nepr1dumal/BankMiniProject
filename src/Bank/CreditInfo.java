package Bank;

import Model.Credit.Credit;
import Model.Credit.CreditType;

import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;

public class CreditInfo implements Saveable {

    private Credit credit;

    public boolean newCredit(CreditType type, BigDecimal value) {
        if (this.credit == null) {
            this.credit = new Credit(type, value);
            return true;
        } else {
            return false;
        }
    }

    public boolean hasCredit() {
        return (credit != null);
    }

    public BigDecimal newPaid(BigDecimal value) {
        BigDecimal change = credit.addPaid(value);
        if (credit.getRemaining().compareTo(new BigDecimal("0")) == 0) {
            System.out.println("Кредит успешно погашен!");
            credit = null;
        }
        return change;
    }

    public boolean updateCounter() {
        if (hasCredit()) {
            if (credit.updateCounter()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getCreditInfo() {
        return credit.getType().getNameOfCredit() + "\n" + "Ставка: " + credit.getType().getPercent().multiply(new BigDecimal("100")).setScale(1) + "%\n" + "Сумма кредита: " + credit.getValue() + "\n" + "Осталось выплатить: " + credit.getRemaining() + "\n" + "Операций до начисления процентов: " + (credit.getType().getOperToPercent() - credit.getCounter() + "\n") + "Выплачено: " + credit.getPaidPart();
    }

    @Override
    public void saveToFile() {
        File file = new File("creditInfo.txt");
        try (FileWriter writer = new FileWriter(file)) {
            if (credit != null) {
                writer.write(credit.getType() + " | " + credit.getValue() + " | " + credit.getCounter() + " | " + credit.getPaidPart());
                System.out.println("Данные успешно сохранены в creditInfo.txt");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных кредита");
        }
    }

    @Override
    public void loadFromFile() {
        File file = new File("creditInfo.txt");
        if (!file.exists()) {
            System.out.println("Кредита не найдено");
            credit = null;
        } else {
            try (Scanner scanner = new Scanner(file)) {
                if (scanner.hasNextLine()) {
                    String[] creditInfo = scanner.nextLine().split(" \\| ");
                    if (creditInfo.length == 4) {
                        try {
                            credit = new Credit(CreditType.valueOf(creditInfo[0]), new BigDecimal(creditInfo[1]), Integer.parseInt(creditInfo[2]), new BigDecimal(creditInfo[3]));
                            System.out.println("Файл данных кредита успешно загружен!");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка при чтении информации о кредите");
                            credit = null;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка при чтении файла данных.");
                credit = null;
            }
        }
    }

    public BigDecimal getValue() {
        return credit.getValue();
    }
}
