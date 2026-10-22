public class Transaction {

    private float value;
    private String name;
    private String date;

    public Transaction(float value, String name, String date){
        this.value = value;
        this.name = name;
        this.date = date;
    }

    public float getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }
}
