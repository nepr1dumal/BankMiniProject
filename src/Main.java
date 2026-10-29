import java.math.BigDecimal;
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
            System.out.println("*** МойБанк ***\n1. Добавить доход\n2. Добавить расход\n3. Перевести деньги контакту\n4. Контакты\n5. Показать историю и баланс\n6. Очистить историю\n7. Выйти из программы");
            String choise = scan.nextLine();
            switch(choise) {
                case "1": {
                    BigDecimal summ = ConsoleInput.readAmount(scan);
                    if (summ == null) {
                        System.out.println("Возвращение...");
                        break;
                    }
                    String name = ConsoleInput.readName(scan, "Название операции: ");
                    main.newTransaction(summ, name);
                    System.out.print("Операция успешно завершена.");
                    break;
                }
                case "2": {
                    BigDecimal summ = ConsoleInput.readAmount(scan);
                    if (summ == null) {
                        System.out.print("Возвращение...");
                        break;
                    }
                    summ = summ.negate();
                    String name = ConsoleInput.readName(scan, "Название операции: ");
                    if (main.tryTransaction(summ)) {
                        System.out.println("Операция успешно завершена.");
                        main.newTransaction(summ, name);
                    } else {
                        System.out.println("Отклонено, недостаточно средств");
                    }
                    break;
                }
                case "3": {
                    String card = ConsoleInput.readCard(scan);
                    if (card == null) {
                        System.out.println("Возвращение...");
                        break;
                    }
                    if (contacts.getName(card) != null) {
                        System.out.println("Получатель: " + contacts.getName(card));
                        BigDecimal summ = ConsoleInput.readAmount(scan);
                        if (summ == null) {
                            System.out.println("Возвращение...");
                            break;
                        }
                        summ = summ.negate();
                        if (main.tryTransaction(summ)) {
                            System.out.println("Успешный перевод");
                            main.newTransaction(summ, "Перевод: " + contacts.getName(card));
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
                            case "2": {
                                String card = ConsoleInput.readCard(scan);
                                if (card == null) {
                                    System.out.println("Возвращение...");
                                    break;
                                }
                                if (contacts.getName(card) == null) {
                                    String name = ConsoleInput.readName(scan, "Имя: ");
                                    contacts.saveContact(card, name);
                                    System.out.println("Контакт сохранён");
                                } else {
                                    System.out.println("Такая карта уже существует, перезаписать контакт?");
                                    boolean choose = ConsoleInput.readYesNo(scan);
                                    if (choose) {
                                        String name = ConsoleInput.readName(scan, "Имя: ");
                                        contacts.saveContact(card, name);
                                        System.out.println("Контакт перезаписан");
                                    } else {
                                        System.out.println("Возвращение...");
                                    }
                                }
                                break;
                            }
                            case "3": {
                                String card = ConsoleInput.readCard(scan);
                                if (card == null) {
                                    System.out.println("Возвращение...");
                                    break;
                                }
                                if (contacts.getName(card) != null) {
                                    System.out.println("Удаление: " + contacts.getName(card));
                                    boolean choose = ConsoleInput.readYesNo(scan);
                                    if (choose) {
                                        contacts.removeContact(card);
                                        System.out.println("Успешное удаление");
                                        break;
                                    } else {
                                        System.out.println("Возвращение...");
                                        break;
                                    }
                                } else {
                                    System.out.println("Контакта с таким номером карты не существует");
                                }
                                break;
                            }
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
                    System.out.println("Вы точно хотите очистить историю?");
                    boolean choose = ConsoleInput.readYesNo(scan);
                    if (choose) {
                        main.clearHistory();
                        System.out.println("Успешно");
                    } else {
                        System.out.println("Возвращение...");
                        break;
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