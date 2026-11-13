package Model.Contact;

public class Contact {

    private String number;
    private String name;

    public Contact(String name, String number) {
        this.number = number;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

}
