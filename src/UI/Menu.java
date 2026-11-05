package UI;

import Bank.Account;
import Bank.Contacts;
import Bank.Stocks;
import Bank.StocksPortfolio;
import Model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class Menu {
    private final Scanner scan;
    private final Account account;
    private final Contacts contacts;
    private final Stocks stocks;
    private final StocksPortfolio stocksPortfolio;

    public Menu(Scanner scan, Account account, Contacts contacts, Stocks stocks, StocksPortfolio stocksPortfolio) {
        this.scan = scan;
        this.account = account;
        this.contacts = contacts;
        this.stocks = stocks;
        this.stocksPortfolio = stocksPortfolio;
    }

    public void operations() {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Добавить доход\n2. Добавить расход\n3. Перевести деньги контакту\n4. Назад");
            switch (scan.nextLine()) {
                case "1": {
                    BigDecimal summ = ConsoleInput.readAmount(scan);
                    if (summ == null) {
                        System.out.println("Возвращение...");
                        break;
                    }
                    String name = ConsoleInput.readName(scan, "Название операции: ");
                    account.newTransaction(new Income(summ, name));
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
                    String name = ConsoleInput.readName(scan, "Название операции: ");
                    if (account.tryTransaction(summ)) {
                        System.out.println("Операция успешно завершена.");
                        account.newTransaction(new Expense(summ, name));
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
                        if (account.tryTransaction(summ)) {
                            System.out.println("Успешный перевод");
                            account.newTransaction(new Transfer(summ, contacts.getName(card)));
                        } else {
                            System.out.println("Недостаточно средств");
                        }
                    } else {
                        System.out.println("Отсутствующий контакт");
                    }
                    break;
                }
                case "4": {
                    System.out.println("Возвращение...");
                    flag = false;
                    break;
                }
                default:
                    System.out.println("Неверная команда");
            }
        }
    }

    public void contactOperation() {
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
                        } else {
                            System.out.println("Возвращение...");
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
    }

    public void showHistoryAndBalance() {
        System.out.println("Тип | Сумма | Название | (Получатель)/(Количество акций) | Дата");
        System.out.println(account.getHistory());
        System.out.println("Баланс: " + account.getWallet().toPlainString() + " RUB");
    }

    public void clearHistory() {
        System.out.println("Вы точно хотите очистить историю?");
        boolean choose = ConsoleInput.readYesNo(scan);
        if (choose) {
            account.clearHistory();
            System.out.println("Успешно");
        } else {
            System.out.println("Возвращение...");
        }
    }

    public void exit() {
        account.saveToFile();
        contacts.saveContacts();
        stocksPortfolio.saveHoldings();
        stocks.saveStocks();
        System.out.println("Всего доброго!");
    }

    public void operationStocks() {
        boolean status = true;
        while (status) {
            System.out.println("1. Купить акции\n2. Продать акции\n3. Назад");
            String choose = scan.nextLine();
            switch (choose) {
                case "1": {
                    stocks.updateStocks();
                    System.out.println(stocks);
                    String numberOfStock = ConsoleInput.readStock(scan, "Выберите акцию для покупки (Enter - отмена): ");
                    if (numberOfStock == null) {
                        System.out.println("Возвращение...");
                        break;
                    }
                    Stock nowStock = stocks.getStock(numberOfStock);
                    if (nowStock == null) {
                        System.out.println("Неверная команда");
                    } else {
                        int number = ConsoleInput.readNumber(scan, "Покупка: " + nowStock.getName() + ". Введите количество акций(Enter - отмена): ");
                        if (number == -404) {
                            System.out.println("Возвращение...");
                            break;
                        }
                        BigDecimal value = nowStock.getPrice().multiply(new BigDecimal(number)).negate();
                        if (account.tryTransaction(value)) {
                            if (stocksPortfolio.updateHoldings(nowStock.getName(), number)) {
                                account.newTransaction(new StockPurchase(value, "Покупка: " + nowStock.getName(), number));
                                System.out.println("Успешная покупка!");
                            }
                        } else {
                            System.out.println("Недостаточно средств");
                        }
                    }
                    break;
                }
                case "2":
                    stocks.updateStocks();
                    if (stocksPortfolio.toString().isEmpty()) {
                        System.out.println("Пустой портфель");
                    } else {
                        System.out.println(stocksPortfolio);
                        String numberOfStock = ConsoleInput.readStock(scan, "Выберите акцию для продажи (Enter - отмена): ");
                        if (numberOfStock == null) {
                            System.out.println("Возвращение...");
                            break;
                        }
                        Holding nowHolding = stocksPortfolio.getHolding(numberOfStock);
                        if (nowHolding == null) {
                            System.out.println("Неверная команда");
                        } else {
                            BigDecimal value = new BigDecimal(String.valueOf(stocks.getStockByName(nowHolding.getName()).getPrice()));
                            int number = ConsoleInput.readNumber(scan, nowHolding.getName() + ". Введите количество акций (Enter - отмена): ");
                            if (number == -404) {
                                System.out.println("Возвращение...");
                                break;
                            }
                            if (stocksPortfolio.updateHoldings(nowHolding.getName(), -number)) {
                                account.newTransaction(new StockSale(value.multiply(new BigDecimal(number)), "Продажа: " + nowHolding.getName(), number));
                                System.out.println("Успешная транзакция");
                            } else {
                                System.out.println("Недостаточно акций");
                            }
                        }
                    }
                    break;
                case "3":
                    System.out.println("Возвращение...");
                    status = false;
                    break;
                default:
                    System.out.println("Неверная команда");
            }
        }
    }
}
