package UI;

import Bank.*;
import Model.Contact.Contact;
import Model.Credit.CreditPayment;
import Model.Credit.CreditReceived;
import Model.Credit.CreditType;
import Model.Stock.Dividend;
import Model.Stock.Holding;
import Model.Stock.Stock;
import Model.Stock.StockPurchase;
import Model.Stock.StockSale;
import Model.Transaction.Expense;
import Model.Transaction.Income;
import Model.Transaction.Transfer;

import java.math.BigDecimal;
import java.util.Scanner;

public class Menu {
    private final Scanner scan;
    private final Account account;
    private final Contacts contacts;
    private final Stocks stocks;
    private final StocksPortfolio stocksPortfolio;
    private final CreditInfo creditInfo;

    public Menu(Scanner scan, Account account, Contacts contacts, Stocks stocks, StocksPortfolio stocksPortfolio, CreditInfo creditInfo) {
        this.scan = scan;
        this.account = account;
        this.contacts = contacts;
        this.stocks = stocks;
        this.stocksPortfolio = stocksPortfolio;
        this.creditInfo = creditInfo;
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
                    updateCounters();
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
                        updateCounters();
                    } else {
                        System.out.println("Отклонено, недостаточно средств");
                    }
                    break;
                }
                case "3": {
                    String list = contacts.toString();
                    if (!list.isEmpty()) {
                        System.out.println(list);
                    } else {
                        System.out.println("Пустой список контактов");
                        break;
                    }
                    String input = ConsoleInput.readStock(scan, "Введите номер контакта из списка (Enter - отмена): ");
                    if (input == null) {
                        System.out.println("Возвращение...");
                        break;
                    }
                    Contact nowContact = contacts.getContactByList(input);
                    if (nowContact != null) {
                        System.out.println("Получатель: " + nowContact.getName());
                        BigDecimal summ = ConsoleInput.readAmount(scan);
                        if (summ == null) {
                            System.out.println("Возвращение...");
                            break;
                        }
                        summ = summ.negate();
                        if (account.tryTransaction(summ)) {
                            System.out.println("Успешный перевод");
                            account.newTransaction(new Transfer(summ, nowContact.getName()));
                            updateCounters();
                        } else {
                            System.out.println("Недостаточно средств");
                        }
                    } else {
                        System.out.println("Неверный номер контакта");
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
            String list = contacts.toString();
            if (!list.isEmpty()) {
                System.out.println(list);
            } else {
                System.out.println("Пустой список контактов");
            }
            System.out.println("1. Добавить контакт\n2. Удалить контакт\n3. Изменить контакт\n4. Назад");
            String selection = scan.nextLine();
            switch (selection) {
                case "1": {
                    String card = ConsoleInput.readCard(scan);
                    if (card == null) {
                        System.out.println("Возвращение...");
                        break;
                    }
                    if (contacts.getContactByNumber(card) == null) {
                        String name = ConsoleInput.readName(scan, "Имя: ");
                        contacts.saveContact(name, card);
                        System.out.println("Контакт сохранён");
                    } else {
                        System.out.println("Контакт с такой картой уже существует, перезаписать контакт?");
                        Contact nowContact = contacts.getContactByNumber(card);
                        boolean choose = ConsoleInput.readYesNo(scan);
                        if (choose) {
                            String name = ConsoleInput.readName(scan, "Имя: ");
                            nowContact.setName(name);
                            System.out.println("Контакт перезаписан");
                        } else {
                            System.out.println("Возвращение...");
                        }
                    }
                    break;
                }
                case "2": {
                    String input = ConsoleInput.readStock(scan, "Введите номер контакта из списка (Enter - отмена): ");
                    if (input == null) {
                        System.out.println("Возвращение...");
                        break;
                    }
                    Contact nowContact = contacts.getContactByList(input);
                    if (nowContact == null) {
                        System.out.println("Неверный номер контакта");
                        break;
                    }
                    System.out.println("Удаление: " + nowContact.getName());
                    if (ConsoleInput.readYesNo(scan)) {
                        contacts.removeContact(nowContact.getNumber());
                        System.out.println("Контакт удалён");
                    } else {
                        System.out.println("Возвращение...");
                    }
                    break;
                }
                case "3": {
                    String input = ConsoleInput.readStock(scan, "Введите номер контакта из списка (Enter - отмена): ");
                    if (input == null) {
                        System.out.println("Возвращение...");
                        break;
                    }
                    Contact nowContact = contacts.getContactByList(input);
                    if (nowContact == null) {
                        System.out.println("Неверный номер контакта");
                        break;
                    }
                    String name = ConsoleInput.readName(scan, "Введите новое имя: ");
                    nowContact.setName(name);
                    System.out.println("Успешное сохранение");
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
                        Integer number = ConsoleInput.readNumber(scan, "Покупка: " + nowStock.getName() + ". Введите количество акций(Enter - отмена): ");
                        if (number == null) {
                            System.out.println("Возвращение...");
                            break;
                        }
                        BigDecimal value = nowStock.getPrice().multiply(new BigDecimal(number)).negate();
                        if (account.tryTransaction(value)) {
                            if (stocksPortfolio.updateHoldings(nowStock.getName(), number)) {
                                account.newTransaction(new StockPurchase(value, "Покупка: " + nowStock.getName(), number));
                                System.out.println("Успешная покупка!");
                                updateCounters();
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
                            Integer number = ConsoleInput.readNumber(scan, nowHolding.getName() + ". Введите количество акций (Enter - отмена): ");
                            if (number == null) {
                                System.out.println("Возвращение...");
                                break;
                            }
                            if (stocksPortfolio.updateHoldings(nowHolding.getName(), -number)) {
                                account.newTransaction(new StockSale(value.multiply(new BigDecimal(number)), "Продажа: " + nowHolding.getName(), number));
                                System.out.println("Успешная транзакция");
                                updateCounters();
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

    private void applyForCredit(CreditType type) {
        System.out.println("Выбран: " + type.getInfo());
        BigDecimal valueOfCredit = ConsoleInput.readAmount(scan);
        if (valueOfCredit == null) {
            System.out.println("Возвращение...");
            return;
        }
        System.out.println(type.getNameOfCredit() + ". Сумма: " + valueOfCredit);
        if (!ConsoleInput.readYesNo(scan)) {
            System.out.println("Возвращение...");
            return;
        }
        creditInfo.newCredit(type, valueOfCredit);
        account.newTransaction(new CreditReceived(valueOfCredit));
        System.out.println("Вы успешно взяли кредит!");
    }

    public void creditOperation() {
        if (!creditInfo.hasCredit()) {
            boolean status = true;
            while (status) {
                System.out.println("Выберите подходящий под вас формат кредита:\n1. " + CreditType.FAMILY.getInfo() + "\n2. " + CreditType.FAST.getInfo() + "\n3. " + CreditType.BUSINESS.getInfo() + "\n4. Назад");
                switch (scan.nextLine()) {
                    case "1": {
                        applyForCredit(CreditType.FAMILY);
                        break;
                    }
                    case "2": {
                        applyForCredit(CreditType.FAST);
                        break;
                    }
                    case "3": {
                        applyForCredit(CreditType.BUSINESS);
                        break;
                    }
                    case "4": {
                        System.out.println("Возвращение...");
                        status = false;
                        break;
                    }
                    default:
                        System.out.println("Неверная команда");
                }
                if (creditInfo.hasCredit()) {
                    status = false;
                }
            }
        } else {
            boolean status = true;
            while(status) {
                System.out.println("1. Информация о кредите\n2. Платеж по кредиту\n3. Назад");
                switch (scan.nextLine()) {
                    case "1" -> {
                        System.out.println(creditInfo.getCreditInfo());
                    }
                    case "2" -> {
                        BigDecimal value = ConsoleInput.readAmount(scan);
                        if (value == null) {
                            System.out.println("Возвращение...");
                            break;
                        }
                        if (account.tryTransaction(value.negate())) {
                            account.newTransaction(new CreditPayment(value.negate()));
                            System.out.println("Успешный платеж!");
                            BigDecimal change = creditInfo.newPaid(value);
                            if (!creditInfo.hasCredit() && change.compareTo(BigDecimal.ZERO) > 0) {
                                account.newTransaction(new Income(change, "Возвращение переплаты оплаты кредита"));
                            }
                            if (!creditInfo.hasCredit()) {
                                status = false;
                            }
                        } else {
                            System.out.println("Недостаточно средств");
                        }
                    }
                    case "3" -> {
                        System.out.println("Возвращение...");
                        status = false;
                    }
                    default -> {
                        System.out.println("Неверная команда");
                    }
                }
            }
        }
    }

    private void updateCounters() {
        if (creditInfo.updateCounter()) {
            System.out.println("Долг по кредиту увеличен, новое тело кредита: " + creditInfo.getValue());
        }
        if (stocksPortfolio.updateDividendPayments()) {
            for (Dividend dividend : stocksPortfolio.dividendsPayment()) {
                account.newTransaction(dividend);
            }
        }
    }
}
