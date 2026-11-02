import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class Stocks {

    private ArrayList<Stock> list = new ArrayList<>();
    private Random random = new Random();

    public Stocks() {
        this.list.add(new Stock("SBER", new BigDecimal("300.00")));
        this.list.add(new Stock("VTBV", new BigDecimal("500.00")));
        this.list.add(new Stock("MGNT", new BigDecimal("200.00")));
        this.list.add(new Stock("VKGR", new BigDecimal("7000.00")));
        this.list.add(new Stock("GAZP", new BigDecimal("50.00")));
        this.list.add(new Stock("X5GR", new BigDecimal("1000.00")));
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

}