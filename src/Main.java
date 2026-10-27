import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

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
            System.out.println("*** МойБанк ***\n1. Добавить доход\n2. Добавить расход\n3. Перевести деньги контакту\n4. Изменить контакты\n5. Показать историю и баланс\n6. Показать список контактов\n7. Очистить историю\n8. Выйти из программы");
            String choise = scan.nextLine();
            switch(choise) {
                case "1":
                    System.out.print("Введите операцию в формате:\nСумма: ");
                    try {
                        String summaStr = scan.nextLine();
                        BigDecimal b = new BigDecimal(summaStr).setScale(2, RoundingMode.HALF_UP);
                        if (b.signum() <= 0) {
                            System.out.println("Сумма должна быть больше нуля");
                            break;
                        }
                        System.out.print("Название: ");
                        String nazv = scan.nextLine();
                        System.out.print("Дата (формат DD/MM/YY): ");
                        String date = scan.nextLine();
                        main.newTransaction(b, nazv, date);
                        System.out.println("Операция успешно завершена.");
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка! Нужно ввести число.");
                    }
                    break;
                case "2":
                    System.out.print("Введите операцию в формате:\nСумма (положительное число): ");
                    try {
                        String summaStr = scan.nextLine();
                        BigDecimal b = new BigDecimal(summaStr).setScale(2, RoundingMode.HALF_UP);
                        if(b.signum() <= 0) {
                            System.out.println("Сумма должна быть больше 0");
                            break;
                        }
                        b = b.negate();
                        System.out.print("Название: ");
                        String nazv = scan.nextLine();
                        System.out.print("Дата (формат DD/MM/YY): ");
                        String date = scan.nextLine();
                        if(main.tryTransaction(b)){
                            System.out.println("Операция успешно завершена.");
                            main.newTransaction(b, nazv, date);
                        } else {
                            System.out.println("Отклонено, недостаточно средств");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка! Нужно ввести число.");
                    }
                    break;
                case "3": {
                    System.out.print("Введите номер карты получателя: ");
                    String number = scan.nextLine().trim();
                    if (contacts.haveContact(number)) {
                        System.out.println("Получатель: " + contacts.getName(number));
                        try {
                            System.out.print("Введите сумму перевода: ");
                            BigDecimal value = new BigDecimal(scan.nextLine()).setScale(2, RoundingMode.HALF_UP);
                            if (value.signum() > 0) {
                                value = value.negate();
                                System.out.print("Введите дату (формат DD/MM/YY): ");
                                String date = scan.nextLine();
                                if (main.tryTransaction(value)) {
                                    System.out.println("Успешный перевод");
                                    main.newTransaction(value, "Перевод " + contacts.getName(number), date);
                                } else {
                                    System.out.println("Недостаточно средств");
                                }
                            } else {
                                System.out.println("Сумма перевода должна быть больше 0");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка! Нужно ввести число.");
                        }
                    } else {
                        System.out.println("Отсутствующий контакт");
                    }
                    break;
                }
                case "4": {
                    System.out.println("1. Добавить контакт\n2. Удалить контакт");
                    String selection = scan.nextLine();
                    switch (selection) {
                        case "1":
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
                        case "2":
                            System.out.print("Введите номер карты (без пробелов): ");
                            String number = scan.nextLine();
                            if (number.matches("\\d{16}")) {
                                if (contacts.haveContact(number)) {
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
                        default:
                            System.out.println("Неверная команда");
                            break;
                    }
                }
                break;
                case "5":
                    System.out.println("Сумма | Название | Дата");
                    System.out.println(main.getHistory());
                    System.out.println("Баланс: " + main.getWallet().toPlainString() + " RUB");
                    break;
                case "6":
                    String list = contacts.getContacts();
                    if (!list.isEmpty()) {
                        System.out.println(list);
                    } else {
                        System.out.println("Пустой список");
                    }
                    break;
                case "7":
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
                case "8":
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