package Bank;

import Model.Stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Stocks {

    private ArrayList<Stock> list = new ArrayList<>();
    private Random random = new Random();

    public Stocks() {
    }

    @Override
    public String toString() {
        int i = 1;
        String spisok = "";
        for (Stock line : list) {
            spisok += i + ": " + line.getName() + " " + line.getPrice() + " RUB" + "\n";
            i++;
        }
        return spisok;
    }

    public void updateStocks() {
        for (Stock line : list) {
            int percent = random.nextInt(-5000, 5001);
            line.updatePrice(percent);
        }
    }

    public Stock getStock(String number) {
        try {
            return this.list.get(Integer.parseInt(number) - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Stock getStockByName(String name) {
        for (Stock line : list) {
            if (name.equals(line.getName())) {
                return line;
            }
        }
        return null;
    }

    public void loadFromFile() {
        File file = new File("stocks.txt");
        if(!file.exists()) {
            System.out.println("Актуальные акции не найдены. Загружаем стартовые.");
            loadStartStocks();
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                try {
                    String[] stock = fileScanner.nextLine().split("\\|", 2);
                    if (stock.length == 2) {
                        this.list.add(new Stock(stock[0], new BigDecimal(stock[1])));
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            if (this.list.isEmpty()) {
                System.out.println("Актуальные акции не найдены. Загружаем стартовые.");
                loadStartStocks();
                return;
            }
            System.out.println("Актуальные акции загружены!");
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка при загрузке файла");
        }
    }

    public void saveStocks() {
        try (FileWriter writer = new FileWriter("stocks.txt")) {
            for (Stock stock : list) {
                writer.write(stock.getName() + "|" + stock.getPrice() + "\n");
            }
            System.out.println("Данные успешно сохранены в stocks.txt");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении акций");
        }

    }

    private void loadStartStocks() {
        this.list.add(new Stock("SBER", new BigDecimal("300.00")));
        this.list.add(new Stock("VTBV", new BigDecimal("500.00")));
        this.list.add(new Stock("MGNT", new BigDecimal("200.00")));
        this.list.add(new Stock("VKGR", new BigDecimal("7000.00")));
        this.list.add(new Stock("GAZP", new BigDecimal("50.00")));
        this.list.add(new Stock("X5GR", new BigDecimal("1000.00")));
    }

}