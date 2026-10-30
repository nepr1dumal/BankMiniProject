import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Account {

    private BigDecimal wallet = BigDecimal.ZERO.setScale(2,RoundingMode.HALF_UP);
    private ArrayList<Transaction> history = new ArrayList<>();
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy");

    public void newTransaction(Transaction t) {
        this.wallet = this.wallet.add(t.getValue());
        this.history.add(t);
    }

    public String getHistory() {
        String a = "";
        for (Transaction el : this.history) {
            a += el + "\n";
        }
        return a;
    }

    public BigDecimal getWallet() {
        return this.wallet;
    }

    public boolean tryTransaction(BigDecimal value) {
        return this.wallet.add(value).signum() >= 0;
    }

    public void clearHistory() {
        this.history.clear();
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("database.txt")) {
            writer.write(this.wallet.toPlainString() + "\n");
            for (Transaction line : this.history) {
                writer.write(line + "\n");
            }
            System.out.println("Данные успешно сохранены в database.txt");

        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных");
        }
    }

    public void loadFromFile() {
        File file = new File("database.txt");
        if (!file.exists()) {
            System.out.println("Файл данных не найден. Начинаем с чистого листа.");
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            if (fileScanner.hasNextLine()) {
                String walletStr = fileScanner.nextLine();
                this.wallet = new BigDecimal(walletStr).setScale(2, RoundingMode.HALF_UP);
            }
            while (fileScanner.hasNextLine()) {
                String[] line = fileScanner.nextLine().split(" \\| ");
                switch (line[0]) {
                    case "INCOME" -> {if (line.length == 4) history.add(new Income(new BigDecimal(line[1]), line[2], line[3])); }
                    case "EXPENSE" -> {if (line.length == 4) history.add(new Expense(new BigDecimal(line[1]), line[2], line[3])); }
                    case "TRANSFER" -> {if (line.length == 5) history.add(new Transfer(new BigDecimal(line[1]), line[3], line[4])); }
                }
            }
            int a = this.history.size();
            System.out.println("Данные успешно загружены! Операций найдено: " + a);
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла данных.");
        }
    }
}