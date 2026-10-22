import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Account {

    private float wallet;
    private ArrayList<String> history = new ArrayList<>();

    public void newTransaction(float value, String name, String date) {
        String transaction = String.valueOf(value) + " | " + name + " | " + date;
        this.wallet += value;
        this.history.add(transaction);
    }

    public String getHistory() {
        String a = "";
        for(String el : this.history) {
            a += el + "\n";
        }
        return a;
    }

    public float getWallet() {
        return this.wallet;
    }

    public boolean tryTranscaction(float value) {
        if(value < 0 && this.wallet + value < 0) {
            return false;
        } else {
            return true;
        }
    }

    public void clearHistory() {
        this.history.clear();
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("database.txt")) {
            writer.write(this.wallet + "\n");
            for (String line : this.history) {
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
                this.wallet = Float.parseFloat(walletStr);
            }
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                this.history.add(line);
            }
            System.out.println("Данные успешно загружены! Операций найдено: " + this.history.size());
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла данных.");
        }
    }

}
