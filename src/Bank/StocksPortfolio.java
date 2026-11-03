package Bank;

import Model.Holding;
import Model.Stock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StocksPortfolio {

    private ArrayList<Holding> list = new ArrayList<>();
    private Stocks stocks;

    public StocksPortfolio(Stocks stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        int i = 1;
        String spisok = "";
        for (Holding line : list) {
            Stock nowStock = stocks.getStockByName(line.getName());
            spisok += i + ": " + line.getName() + " " + line.getQuantity() + " штук. Цена: " + nowStock.getPrice() + "\n";
            i++;
        }
        return spisok;
    }

    public void loadFromFile() {
        File file = new File("holdings.txt");
        if(!file.exists()) {
            System.out.println("Портфель акций не найден. Начинаем с пустого.");
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                try {
                    String[] holdingInfo = fileScanner.nextLine().split("\\|", 2);
                    if (holdingInfo.length == 2) {
                        this.list.add(new Holding(holdingInfo[0], Integer.parseInt(holdingInfo[1])));
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            System.out.println("Портфель загружен!");
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла");
        }
    }

    public void saveHoldings() {
        try (FileWriter writer = new FileWriter("holdings.txt")) {
            for(Holding holding : list) {
                writer.write(holding.getName() + "|" + holding.getQuantity() + "\n");
            }
            System.out.println("Данные успешно сохранены в holdings.txt");

        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных");
        }
    }


}