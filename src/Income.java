import java.math.BigDecimal;

public class Income extends Transaction{

    private static final String TYPE = "INCOME";

    public Income(BigDecimal value, String name, String date) {
        super(value, name, date);
    }

    public Income(BigDecimal value, String name) {
        super(value, name);
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
