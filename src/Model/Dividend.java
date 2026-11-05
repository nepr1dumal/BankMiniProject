package Model;

import java.math.BigDecimal;

public class Dividend extends StockOperation{

    private static final String TYPE = "DIVIDEND";

    public Dividend(BigDecimal value, String name, int quantity) {
        super(value, name, quantity);
    }

    public Dividend(BigDecimal value, String name, String date, int quantity) {
        super(value, name, date, quantity);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
