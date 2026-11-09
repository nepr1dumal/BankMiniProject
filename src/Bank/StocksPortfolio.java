package Bank;

import Model.Dividend;
import Model.Holding;
import Model.Stock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class StocksPortfolio implements Saveable{

    private ArrayList<Holding> list = new ArrayList<>();
    private Stocks stocks;
    private int dividendPaymentTime;
    private final static int DIVIDEND_BORDER = 10;
    private final static BigDecimal DIVIDEND_PERCENT = new BigDecimal("10.00").divide(new BigDecimal("100.00"));

    public StocksPortfolio(Stocks stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        int i = 1;
        String spisok = "";
        for (Holding line : list) {
            Stock nowStock = stocks.getStockByName(line.getName());
            spisok += i + ": " + line.getName() + ". Количество - " + line.getQuantity() + ". Средняя цена покупки: " + line.getAverageValue() + ". Цена: " + nowStock.getPrice() + ". Разница: " + nowStock.getPrice().subtract(line.getAverageValue()) + "\n";
            i++;
        }
        return spisok;
    }

    public boolean updateHoldings(String name, int quantity) {
        if (quantity != 0) {
            Holding nowHolding = null;
            for (Holding holding : list) {
                if (holding.getName().equals(name)) {
                    nowHolding = holding;
                    }
                }
            if (nowHolding != null) {
                if (nowHolding.getQuantity() + quantity > 0) {
                    nowHolding.changeQuantity(nowHolding.getQuantity() + quantity, stocks.getStockByName(nowHolding.getName()).getPrice());
                    return true;
                } else if (nowHolding.getQuantity() + quantity == 0) {
                    this.list.remove(nowHolding);
                    return true;
                } else {
                    return false;
                }
            } else {
                if (quantity > 0) {
                    this.list.add(new Holding(name, quantity, stocks.getStockByName(name).getPrice()));
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public void loadFromFile() {
        File file = new File("holdings.txt");
        if(!file.exists()) {
            System.out.println("Портфель акций не найден. Начинаем с пустого.");
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            try {
                String[] dividendPayment = fileScanner.nextLine().split("/", 2);
                this.dividendPaymentTime = Integer.parseInt(dividendPayment[0]);
            } catch (NumberFormatException e) {
                this.dividendPaymentTime = 0;
            }
            while (fileScanner.hasNextLine()) {
                try {
                    String[] holdingInfo = fileScanner.nextLine().split(" \\| ", 3);
                    if (holdingInfo.length == 3) {
                        this.list.add(new Holding(holdingInfo[0], Integer.parseInt(holdingInfo[1]), new BigDecimal(holdingInfo[2])));
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

    @Override
    public void saveToFile() {
        try (FileWriter writer = new FileWriter("holdings.txt")) {
            writer.write(this.dividendPaymentTime + "/" + DIVIDEND_BORDER + "\n");
            for(Holding holding : list) {
                writer.write(holding.getName() + " | " + holding.getQuantity() + " | " + holding.getAverageValue() + "\n");
            }
            System.out.println("Данные успешно сохранены в holdings.txt");

        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных");
        }
    }

    public Holding getHolding(String number) {
        try {
            return this.list.get(Integer.parseInt(number) - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public boolean updateDividendPayments() {
        this.dividendPaymentTime++;
        if (this.dividendPaymentTime == DIVIDEND_BORDER) {
            dividendPaymentTime = 0;
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Dividend> dividendsPayment() {
        ArrayList<Dividend> values = new ArrayList<>();
        for (Holding holding : this.list) {
            values.add(new Dividend(stocks.getStockByName(holding.getName()).getPrice().multiply(new BigDecimal(holding.getQuantity())).multiply(DIVIDEND_PERCENT).setScale(2, RoundingMode.HALF_UP), "Выплата дивидендов: " + holding.getName(), holding.getQuantity()));
        }
        return values;
    }

}