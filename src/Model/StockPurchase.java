package Model;

import java.math.BigDecimal;

public class StockPurchase extends StockOperation {

    private static final String TYPE = "STOCK PURCHASE";

    public StockPurchase(BigDecimal value, String name, int quantity) {
        super(value, name, quantity);
    }

    public StockPurchase(BigDecimal value, String name, String date, int quantity) {
        super(value, name, date, quantity);
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
