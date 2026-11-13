package Model.Stock;

import Model.Transaction.TransactionType;

import java.math.BigDecimal;

public class StockPurchase extends StockOperation {

    public StockPurchase(BigDecimal value, String name, int quantity) {
        super(value, name, quantity);
    }

    public StockPurchase(BigDecimal value, String name, String date, int quantity) {
        super(value, name, date, quantity);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.STOCK_PURCHASE;
    }

}
