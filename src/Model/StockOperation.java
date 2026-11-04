package Model;

import java.math.BigDecimal;

public abstract class StockOperation extends Transaction{

    private int quantity;

    public StockOperation(BigDecimal value, String name, int quantity) {
        super(value, name);
        this.quantity = quantity;
    }

    public StockOperation(BigDecimal value, String name, String date, int quantity) {
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
}

