package Model.Credit;

import Model.Transaction.Transaction;
import Model.Transaction.TransactionType;

import java.math.BigDecimal;

public class CreditPayment extends Transaction {

    public CreditPayment(BigDecimal value) {
        super(value, "Выплата кредита");
    }

    public CreditPayment(BigDecimal value, String name, String date) {
        super(value, name, date);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.CREDIT_PAYMENT;
    }

}
