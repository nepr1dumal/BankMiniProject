package Model.Credit;

import Model.Transaction.Transaction;
import Model.Transaction.TransactionType;

import java.math.BigDecimal;

public class CreditReceived extends Transaction {

    public CreditReceived(BigDecimal value) {
        super(value, "Выдача кредита");
    }

    public CreditReceived(BigDecimal value, String name, String date) {
        super(value, name, date);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.CREDIT_RECEIVED;
    }
}
