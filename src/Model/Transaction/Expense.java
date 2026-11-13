package Model.Transaction;

import java.math.BigDecimal;

public class Expense extends Transaction {

    public Expense(BigDecimal value, String name, String date) {
        super(value, name, date);
    }

    public Expense(BigDecimal value, String name) {
        super(value, name);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.EXPENSE;
    }

}
