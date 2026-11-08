package Model;

import java.math.BigDecimal;

public class Dividend extends StockOperation{

    public Dividend(BigDecimal value, String name, int quantity) {
        super(value, name, quantity);
    }

    public Dividend(BigDecimal value, String name, String date, int quantity) {
        super(value, name, date, quantity);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.DIVIDEND;
    }
}
