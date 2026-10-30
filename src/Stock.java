import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class Stock {

    private LinkedHashMap<String, BigDecimal> stocks = new LinkedHashMap<>();

    public Stock() {
        stocks.put("SBER", new BigDecimal("300.00"));
        stocks.put("VTBV", new BigDecimal("1300.00"));
        stocks.put("MGNT", new BigDecimal("500.00"));
        stocks.put("X5GR", new BigDecimal("250.00"));
        stocks.put("GAZP", new BigDecimal("10.00"));
        stocks.put("VKGR", new BigDecimal("10000.00"));
        }
}