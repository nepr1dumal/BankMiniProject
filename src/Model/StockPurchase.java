package Model;

import java.math.BigDecimal;

public class StockPurchase extends Transaction {

    private static final String TYPE = "STOCK PURCHASE";
    private int quantity;

    public StockPurchase(BigDecimal value, String name, int quantity) {
        super(value, name);
        this.quantity = quantity;
    }

    public StockPurchase(BigDecimal value, String name, String date, int quantity) {
        super(value, name, date);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return getType() + " | " + getValue().toPlainString() + " | " + getName() + " | " + getQuantity() + " | " + getDate();
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
