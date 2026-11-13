package Model.Credit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Credit {

    private final CreditType type;
    private BigDecimal value;
    private BigDecimal paidPart;
    private int counter;

    public Credit(CreditType type, BigDecimal value) {
        this.type = type;
        this.value = value.multiply(type.getPercent()).add(value).setScale(2, RoundingMode.HALF_UP);
        this.counter = 0;
        this.paidPart = new BigDecimal("0.00");
    }

    public Credit(CreditType type, BigDecimal value, int counter, BigDecimal paidPart) {
        this.type = type;
        this.value = value;
        this.counter = counter;
        this.paidPart = paidPart;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getPaidPart() {
        return paidPart;
    }

    public BigDecimal addPaid(BigDecimal paidPart) {
        if (paidPart.setScale(2, RoundingMode.HALF_UP).add(this.paidPart).compareTo(value) < 0) {
            this.paidPart = this.paidPart.add(paidPart).setScale(2, RoundingMode.HALF_UP);
            return new BigDecimal("0");
        } else {
            this.paidPart = this.paidPart.add(paidPart).setScale(2, RoundingMode.HALF_UP);
            BigDecimal change = this.paidPart.subtract(value);
            this.paidPart = value;
            return change;
        }
    }

    public boolean updateCounter() {
        this.counter++;
        if (this.counter == type.getOperToPercent()) {
            deadline();
            this.counter = 0;
            return true;
        } else {
            return false;
        }
    }
    private void deadline() {
        this.value = this.value.subtract(this.paidPart).multiply(type.getSurcharge()).setScale(2, RoundingMode.HALF_UP).add(value);
    }

    public CreditType getType() {
        return type;
    }

    public int getCounter() {
        return counter;
    }

    public BigDecimal getRemaining() {
        return value.subtract(paidPart);
    }
}
