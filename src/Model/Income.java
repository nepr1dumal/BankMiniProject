package Model;

import java.math.BigDecimal;

public class Income extends Transaction {

    public Income(BigDecimal value, String name, String date) {
        super(value, name, date);
    }

    public Income(BigDecimal value, String name) {
        super(value, name);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.INCOME;
    }

}
