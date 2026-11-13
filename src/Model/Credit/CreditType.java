package Model.Credit;

import java.math.BigDecimal;

public enum CreditType {

    FAST("Быстрый", new BigDecimal("0.05"), 5, new BigDecimal("0.20")),
    FAMILY("Семейный", new BigDecimal("0.10"), 30, new BigDecimal("0.07")),
    BUSINESS("Бизнес-класс", new BigDecimal("0.15"), 45, new BigDecimal("0.05"));

    private final String nameOfCredit;
    private final BigDecimal percent;
    private final int operToPercent;
    private final BigDecimal surcharge;

    CreditType(String nameOfCredit, BigDecimal percent, int operToPercent, BigDecimal surcharge) {
        this.nameOfCredit = nameOfCredit;
        this.percent = percent;
        this.operToPercent = operToPercent;
        this.surcharge = surcharge;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public int getOperToPercent() {
        return operToPercent;
    }

    public String getNameOfCredit() {
        return nameOfCredit;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public String getInfo() {
        return getNameOfCredit() + ". Начальная ставка: " + getPercent().multiply(new BigDecimal("100")).setScale(1) + "%. Операций до начисления пени: " + getOperToPercent() + ". Дополнительные проценты в случае просрочки: " + getSurcharge().multiply(new BigDecimal("100")).setScale(1) + "%";
    }
}

