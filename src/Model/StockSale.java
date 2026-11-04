package Model;

import java.math.BigDecimal;

public class StockSale extends StockOperation{

    private static final String TYPE = "STOCK SALE";

    public StockSale(BigDecimal value, String name, int quantity) {
        super(value, name, quantity);
    }

    public StockSale(BigDecimal value, String name, String date, int quantity) {
        super(value, name, date, quantity);
    }

    @Override
    public String getType() {
        return TYPE;
    }

}