import java.math.BigDecimal;

public class Expense extends Transaction{

    private static final String TYPE = "EXPENSE";

    public Expense(BigDecimal value, String name, String date) {
        super(value, name, date);
    }

    public Expense(BigDecimal value, String name) {
        super(value, name);
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
