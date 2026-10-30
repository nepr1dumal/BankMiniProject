import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Transaction {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yy");
    private String name;
    private String date;
    private BigDecimal value;


    protected Transaction(BigDecimal value, String name) {
        this.value = value;
        this.name = name;
        this.date = LocalDate.now().format(FORMAT);
    }

    protected Transaction(BigDecimal value, String name, String date) {
        this.value = value;
        this.name = name;
        this.date = date;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " | " + value.toPlainString() + " | " + name + " | " + date;
    }

}

