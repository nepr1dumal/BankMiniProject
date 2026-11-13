package Bank;

import Model.Contact.Contact;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Contacts implements Saveable{

    private ArrayList<Contact> contacts = new ArrayList<>();

    public void saveContact(String name, String number) {
        this.contacts.add(new Contact(name, number));
    }

    @Override
    public String toString() {
        String a = "";
        int i = 1;
        for (Contact contact : contacts) {
            a += i + ": " + contact.getName() + " | " + contact.getNumber() + "\n";
            i++;
        }
        return a;
    }

    @Override
    public void saveToFile() {
        try (FileWriter writer = new FileWriter("contacts.txt")) {
            for(Contact contact : contacts) {
                writer.write(contact.getName() + " | " + contact.getNumber() + "\n");
            }
            System.out.println("Данные успешно сохранены в contacts.txt");

        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных");
        }
    }

    @Override
    public void loadFromFile() {
        File file = new File("contacts.txt");
        if(!file.exists()) {
            System.out.println("Список контактов не найден. Начинаем с пустого.");
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] cards = fileScanner.nextLine().split(" \\| ", 2);
                if (cards.length == 2 && cards[1].matches("\\d{16}")) {
                    contacts.add(new Contact(cards[0], cards[1]));
                }
            }
            System.out.println("Контакты загружены!");
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла");
        }
    }

    public boolean haveContact(String number) {
        for (Contact contact : contacts) {
            if (contact.getNumber().equals(number)) {
                return true;
            }
        }
        return false;
    }

    public Contact getContactByNumber(String number) {
        for (Contact contact : contacts) {
            if (contact.getNumber().equals(number)) {
                return contact;
            }
        }
        return null;
    }

    public boolean removeContact(String number) {
        for (Contact contact : contacts) {
            if (contact.getNumber().equals(number)) {
                this.contacts.remove(contact);
                return true;
            }
        }
        return false;
    }

    public Contact getContactByList(String numberInList) {
        try {
            return contacts.get(Integer.parseInt(numberInList) - 1);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void renameContact(String name, Contact contact) {
        contact.setName(name);
    }
}



