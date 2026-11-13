package Model.Stock;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Holding {

    private String name;
    private int quantity;
    private BigDecimal averageValue;

    public Holding(String name, int quantity, BigDecimal averageValue) {
        this.name = name;
        this.quantity = quantity;
        this.averageValue = averageValue;
    }

    public void changeQuantity(int quantity, BigDecimal value) {
        if (this.quantity < quantity) {
            this.averageValue = (this.averageValue.multiply(new BigDecimal(this.quantity)).add(value.multiply(new BigDecimal(quantity-this.quantity)))).divide(new BigDecimal(quantity), 2, RoundingMode.HALF_UP);
        }
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAverageValue() {
        return this.averageValue;
    }
}