import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Main {

    public static void main(String[] args) {
        Account main = new Account();
        Contacts contacts = new Contacts();
        main.loadFromFile();
        contacts.loadContacts();
        boolean flag = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("Добро пожаловать!");
        while(flag){
            System.out.println("*** МойБанк ***\n1. Добавить доход\n2. Добавить расход\n3. Перевести деньги контакту\n4. Контакты\n5. Показать историю и баланс\n6. Очистить историю\n7. Выйти из программы");
            String choise = scan.nextLine();
            switch(choise) {
                case "1": {
                    BigDecimal summ = ConsoleInput.readAmount(scan);
                    if (summ == null) {
                        System.out.println("Возвращение...");
                        break;
                    }
                    System.out.print("Название: ");
                    String nazv = scan.nextLine();
                    System.out.print("Дата (формат DD/MM/YY): ");
                    String date = scan.nextLine();
                    main.newTransaction(summ, nazv, date);
                    System.out.println("Операция успешно завершена.");
                    break;
                }
                case "2": {
                    BigDecimal summ = ConsoleInput.readAmount(scan);
                    if (summ == null) {
                        System.out.println("Возвращение...");
                        break;
                    }
                    summ = summ.negate();
                    System.out.print("Название: ");
                    String nazv = scan.nextLine();
                    System.out.print("Дата (формат DD/MM/YY): ");
                    String date = scan.nextLine();
                    if (main.tryTransaction(summ)) {
                        System.out.println("Операция успешно завершена.");
                        main.newTransaction(summ, nazv, date);
                    } else {
                        System.out.println("Отклонено, недостаточно средств");
                    }
                    break;
                }
                case "3": {
                    System.out.print("Введите номер карты получателя: ");
                    String number = scan.nextLine().trim();
                    if (contacts.getName(number) != null) {
                        System.out.println("Получатель: " + contacts.getName(number));
                        BigDecimal summ = ConsoleInput.readAmount(scan);
                        if (summ == null) {
                            System.out.println("Возвращение...");
                            break;
                        }
                        summ = summ.negate();
                        System.out.print("Введите дату (формат DD/MM/YY): ");
                        String date = scan.nextLine();
                        if (main.tryTransaction(summ)) {
                            System.out.println("Успешный перевод");
                            main.newTransaction(summ, "Перевод: " + contacts.getName(number), date);
                        } else {
                            System.out.println("Недостаточно средств");
                        }
                    } else {
                        System.out.println("Отсутствующий контакт");
                    }
                    break;
                }
                case "4": {
                    boolean status = true;
                    while (status) {
                        System.out.println("1. Список контактов\n2. Добавить контакт\n3. Удалить контакт\n4. Назад");
                        String selection = scan.nextLine();
                        switch (selection) {
                            case "1":
                                String list = contacts.getContacts();
                                if (!list.isEmpty()) {
                                    System.out.println(list);
                                } else {
                                    System.out.println("Пустой список");
                                }
                                break;
                            case "2":
                                System.out.print("Введите номер карты (без пробелов): ");
                                String card = scan.nextLine();
                                if (card.matches("\\d{16}")) {
                                    if (contacts.getName(card) == null) {
                                        System.out.print("Введите имя: ");
                                        String name = scan.nextLine().trim();
                                        if (name.isEmpty()) {
                                            System.out.println("Имя не может быть пустым");
                                            break;
                                        }
                                        contacts.saveContact(card, name);
                                        System.out.println("Контакт сохранён");
                                    } else {
                                        System.out.println("Такая карта уже существует, перезаписать контакт? Y/N");
                                        String choose = scan.nextLine().toUpperCase();
                                        switch (choose) {
                                            case "Y":
                                                System.out.print("Введите имя: ");
                                                String name = scan.nextLine().trim();
                                                if (name.isEmpty()) {
                                                    System.out.println("Имя не может быть пустым");
                                                    break;
                                                }
                                                contacts.saveContact(card, name);
                                                System.out.println("Контакт перезаписан");
                                                break;
                                            case "N":
                                                System.out.println("Отменено");
                                                break;
                                            default:
                                                System.out.println("Неверная команда");
                                        }
                                    }
                                } else {
                                    System.out.println("Неправильный формат карты");
                                }
                                break;
                            case "3":
                                System.out.print("Введите номер карты (без пробелов): ");
                                String number = scan.nextLine();
                                if (number.matches("\\d{16}")) {
                                    if (contacts.getName(number) != null) {
                                        System.out.println("Удаление: " + contacts.getName(number) + ". Y/N?");
                                        String a = scan.nextLine().trim().toUpperCase();
                                        switch (a) {
                                            case "Y":
                                                contacts.removeContact(number);
                                                System.out.println("Успешное удаление");
                                                break;
                                            case "N":
                                                System.out.println("Возвращение...");
                                                break;
                                            default:
                                                System.out.println("Неверный выбор");
                                        }
                                    } else {
                                        System.out.println("Контакта с таким номером карты не существует");
                                    }
                                } else {
                                    System.out.println("Неверный формат карты");
                                }
                                break;
                            case "4":
                                System.out.println("Возвращение...");
                                status = false;
                                break;
                            default:
                                System.out.println("Неверная команда");
                        }
                    }
                    break;
                }
                case "5":
                    System.out.println("Сумма | Название | Дата");
                    System.out.println(main.getHistory());
                    System.out.println("Баланс: " + main.getWallet().toPlainString() + " RUB");
                    break;
                case "6":
                    System.out.println("Вы точно хотите очистить историю? Y/N");
                    String a = scan.nextLine().toUpperCase();
                    switch (a){
                        case "Y":
                            main.clearHistory();
                            System.out.println("Успешно");
                            break;
                        case "N":
                            System.out.println("Возвращение...");
                            break;
                        default:
                            System.out.println("Неверная операция");
                    }
                    break;
                case "7":
                    main.saveToFile();
                    contacts.saveContacts();
                    flag = false;
                    System.out.println("Всего доброго!");
                    break;

                default:
                    System.out.println("Неверный выбор, попробуйте снова.\n");
            }
        }

    }
}