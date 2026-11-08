package Model;

import java.math.BigDecimal;

public class Transfer extends Transaction {

    private String nameOfUser;

    public Transfer(BigDecimal value, String date, String nameOfUser) {
        super(value, "Перевод", date);
        this.nameOfUser = nameOfUser;
    }

    public Transfer(BigDecimal value, String nameOfUser) {
        super(value, "Перевод");
        this.nameOfUser = nameOfUser;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.TRANSFER;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    @Override
    public String toString() {
        return getType() + " | " + getValue().toPlainString() + " | " + getName() + " | " + getNameOfUser() + " | " + getDate();
    }

}
