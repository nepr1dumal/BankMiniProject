package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Stock {

    private String name;
    private BigDecimal price;

    public Stock(String name, BigDecimal value) {
        this.name = name;
        this.price = value;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void updatePrice(int percent) {
        BigDecimal dDPercent = new BigDecimal(percent);
        dDPercent = dDPercent.add(new BigDecimal(100000)).divide(new BigDecimal(100000));
        setPrice(this.price.multiply(dDPercent).setScale(2, RoundingMode.HALF_UP));
    }
}
