import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Contacts {

    private HashMap<String, String> contacts = new HashMap<>();

    public void saveContact(String number, String name) {
        this.contacts.put(number, name);
    }

    public String getContacts() {
        String a = "";
        for (Map.Entry<String, String> contact : contacts.entrySet()) {
            a += contact.getValue() + "|" + contact.getKey() + "\n";
        }
        return a;
    }

    public void saveContacts() {
        try (FileWriter writer = new FileWriter("contacts.txt")) {
            for(Map.Entry<String, String> contact : contacts.entrySet()) {
                writer.write(contact.getKey() + "|" + contact.getValue() + "\n");
            }
            System.out.println("Данные успешно сохранены в contacts.txt");

        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных");
        }
    }

    public void loadContacts() {
        File file = new File("contacts.txt");
        if(!file.exists()) {
            System.out.println("Список контактов не найден. Начинаем с пустого.");
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] cards = fileScanner.nextLine().split("\\|", 2);
                if (cards.length == 2) {
                    contacts.put(cards[0], cards[1]);
                }
            }
            System.out.println("Контакты загружены!");
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла");
        }
    }

    public boolean haveContact(String number) {
        String a = this.contacts.get(number);
        if (a == null) {
            return false;
        } else {
            return true;
        }
    }

    public String getName(String number) {
        return this.contacts.get(number);
    }

    public void removeContact(String number) {
        this.contacts.remove(number);
    }

}



